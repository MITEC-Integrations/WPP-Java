package mx.com.mit.paygen;

import static java.lang.String.format;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

import javax.xml.bind.JAXBException;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import mx.com.mit.paygen.exception.WPPException;
import mx.com.mit.paygen.models.GenUrlResponse;
import mx.com.mit.paygen.models.PaymentData;
import mx.com.mit.paygen.models.PaymentResponse;
import mx.com.mit.paygen.models.validators.PaymentValidator;
import mx.com.mit.paygen.util.AesHelper;
import mx.com.mit.paygen.util.ErrorResponseParser;
import mx.com.mit.paygen.util.UrlConnectionHelper;
import mx.com.mit.paygen.util.XMLHelper;
import mx.com.mit.paygen.validation.Validation;

/**
 * Clase encargada de enviar solicitudes y procesar las respuestas del generador
 * de ligas.
 */
@Log
public final class WppClient {

	private URL endpoint;
	private String id;
	private AesHelper aesHelper;

	private Validation<PaymentData> validator = new PaymentValidator();

	/**
	 * Crea un cliente para enviar solicitudes al <i>endpoint</i> dado
	 * 
	 * @param endpoint Url al que se envia la solicitud de generación de ligas.
	 * @param id       Identificador proporcionado por WebPay Plus.
	 * @param key      Llave de cifrado en HEX que proporciona WebPay Plus.
	 */
	public WppClient(URL endpoint, String id, String key) {
		this.endpoint = endpoint;
		this.id = id;
		aesHelper = new AesHelper(key, AesHelper.MODE, AesHelper.PADDING);
	}

	/**
	 * Obtiene la liga de pagos con los datos proporcionados por payment.
	 * 
	 * @param @see {@link mx.com.mit.paygen.models.PaymentData payment} Objeto con
	 *             los datos a enviar a WebPay PLUS.
	 * @returns URL para realizar el pago.
	 */
	public URL getUrlPayment(PaymentData payment) {
		validator.test(payment).throwIfInvalid();
		String body = buildRequest(payment);
		body = "xml=" + URLEncoder.encode(body, StandardCharsets.UTF_8);
		String xmlResponse = UrlConnectionHelper.urlSendRecieve(endpoint.toString(), body);
		return processResponse(xmlResponse);
	}

	/**
	 * Obtiene la url de pago de la respuesta del servidor
	 * 
	 * @param xmlResponse respuesta del servidor
	 * @return URL de pago.
	 */
	@SneakyThrows
	private URL processResponse(String xmlResponse) {
		xmlResponse = decryptResponse(xmlResponse);
		GenUrlResponse urlResponse = null;
		try {
			urlResponse = XMLHelper.parse(xmlResponse, GenUrlResponse.class);
			log.fine("" + urlResponse);
		} catch (JAXBException e) {
			throw new WPPException(format("Could not parse response from server %s", xmlResponse));
		}

		if (!"success".equals(urlResponse.getCdResponse())) {
			throw new WPPException(urlResponse.getNbResponse());
		}
		return new URL(urlResponse.getNbUrl());
	}

	/**
	 * Descifra los mensajes enviados del servidor de pagos.
	 * 
	 * @param bodyResponse Mensaje enviado por el servidor
	 * @return Cadena descifrada
	 */
	private String decryptResponse(String bodyResponse) {
		Objects.requireNonNull(bodyResponse);
		if (bodyResponse.startsWith("strResponse=")) {
			bodyResponse = bodyResponse.replace("strResponse=", "");
		}
		if (bodyResponse.contains("%")) {
			bodyResponse = URLDecoder.decode(bodyResponse, StandardCharsets.UTF_8);
		}
		bodyResponse = bodyResponse.replaceAll("[\\r\\n\\s]", "");
		if (!isBase64(bodyResponse)) {
			throw new WPPException("Error al enviar el XML", ErrorResponseParser.parse(bodyResponse));
		}

		bodyResponse = aesHelper.decrypt(bodyResponse);
		return bodyResponse;
	}

	/**
	 * Descifra el resultado de pago y lo convierte a un objeto de tipo
	 * PaymentResponse
	 * 
	 * @param bodyResponse Mensaje cifrado en base64 del servidor.
	 * @return @see {@link mx.com.mit.paygen.models.PaymentResponse}
	 */
	public PaymentResponse processAfterPaymentResponse(String bodyResponse) {
		bodyResponse = decryptResponse(bodyResponse);
		PaymentResponse response = null;
		try {
			response = XMLHelper.parse(bodyResponse, PaymentResponse.class);
		} catch (JAXBException e) {
			throw new WPPException(
					format("Error when converting xml response to PaymentResponse object %s", e.getMessage()));
		}
		return response;
	}

	/**
	 * Convierte el objeto payment a una cadena XML con el formato requerido para
	 * generar una liga de pagos.
	 * 
	 * @param payment Contenedor de datos para generar el XML
	 * @return Cadena XML para enviar la solicitud al servidor
	 */
	private String buildRequest(PaymentData payment) {
		String xml = "";
		try {
			xml = XMLHelper.format(payment, "UTF-8", false);
		} catch (JAXBException | IOException e) {
			throw new WPPException("Error when converting payment to XML", e);
		}
		log.fine(format("xml before crypt %s", xml));
		xml = aesHelper.encrypt(xml);
		log.fine(format("xml after crypt %s", xml));

		ParentPgs pgs = new ParentPgs();
		pgs.setData(xml);
		pgs.setData0(id);

		try {
			xml = XMLHelper.format(pgs, "UTF-8", false);
			xml = xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
		} catch (JAXBException | IOException e) {
			throw new WPPException("Error when converting pgs component to XML", e);
		}
		return xml;
	}

	/**
	 * Indica si una cadena esta codificada en base64.
	 * 
	 * @param message Cadena a verificar
	 * @return <b>true</b> si la cadena tiene formato base64.
	 */
	private boolean isBase64(String message) {
		try {
			Base64.getDecoder().decode(message);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	/**
	 * Verifica que el objeto cumpla con los requisitos necesarios para enviar al
	 * endpoint.
	 * 
	 * @param Cualquier @see mx.com.mit.paygen.validation.Validation validation de
	 *                  tipo @see {@link mx.com.mit.paygen.models.PaymentData
	 *                  PaymentData}
	 */
	public void setValidator(Validation<PaymentData> validation) {
		this.validator = validation;
	}
}
