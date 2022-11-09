package mx.com.mit.paygen.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * Contenedor XML del elemento "P"
 * 
 * @see <a href="https://sandboxpol.mit.com.mx/generar">Generando una liga de
 *      cobro</a>
 *
 */
@XmlRootElement(name = "P")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "business", "paymentMethod", "version", "url", "data3ds", "additionalData" })
@Builder
@Data
public class PaymentData {

	public static enum FormaPagoType {
		GPY, APY, C2P, COD, TCD, BNPL
	};

	/**
	 * Elemento {@link mx.com.mit.paygen.models.BusinessData business} del xml
	 */
	@XmlElement
	private BusinessData business;

	/**
	 * Forma de pago que se visualiza en el formulario.
	 */
	@XmlElement(name = "nb_fpago")
	private FormaPagoType paymentMethod;

	@XmlElement
	private final String version = "IntegraWPP";

	/**
	 * Elemento {@link mx.com.mit.paygen.models.UrlData url} del xml
	 */
	@XmlElement
	private UrlData url;

	/**
	 * Elemento {@link mx.com.mit.paygen.models.D3DSData data3ds} del xml
	 */
	@XmlElement
	private D3DSData data3ds;

	/**
	 * Elemento {@link mx.com.mit.paygen.models.DatosAdicionalesData
	 * datos_adicionales} del xml
	 */
	@XmlElement(name = "datos_adicionales")
	private DatosAdicionalesData additionalData;

	@Tolerate
	PaymentData() {

	}

}
