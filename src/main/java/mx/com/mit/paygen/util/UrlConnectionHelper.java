package mx.com.mit.paygen.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import mx.com.mit.paygen.exception.ConectionException;
import mx.com.mit.paygen.exception.RequestException;
import mx.com.mit.paygen.exception.ResponseException;

/**
 * Clase que permite establecer conexion con un servidor y enviar/recibir un
 * mensaje
 */
public class UrlConnectionHelper {

	private static Logger log = Logger.getLogger(UrlConnectionHelper.class.getName());

	/**
	 * Establece conexion con la url para enviar un mensaje
	 * 
	 * @param url             Url de conexion
	 * @param mensaje         Mensaje a enviar
	 * @param waitForResponse true para esperar respuesta.
	 * @return respuesta obtenida del server en caso de haber esperado por la
	 *         respuesta.
	 */
	public static String urlSendRecieve(String url, String mensaje, boolean waitForResponse) {
		return urlSendRecieve(url, mensaje, waitForResponse, 0);
	}

	/**
	 * Establece comunicacion con la url proporcionada para enviar el mensaje
	 * 
	 * @param url             Url con la que se establece comunicacion
	 * @param mensaje         mensaje que se envia al host
	 * @param waitForResponse indica si se va a leer la respuesta del server
	 * @return respuesta obtenida del server en caso de que se haya leido.
	 * @throws IOException
	 */
	public static String urlSendRecieve(String url, String mensaje, boolean waitForResponse, int maxTimeOut) {
		log.info("Conectandose a url: " + url);
		log.fine("urlDatos : " + mensaje);

		URLConnection connection = null;

		try {
			URL serviceUrl = new URL(url);
			connection = serviceUrl.openConnection();

			connection.setDoOutput(true);
			connection.setConnectTimeout(maxTimeOut);
			connection.setReadTimeout(maxTimeOut);
			connection.setRequestProperty("Method", "POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Length", Integer.toString(mensaje.length()));
			connection.connect();
		} catch (IOException ce) {
			throw new ConectionException("Error with connection: " + ce.getMessage());
		}

		sendRequest(mensaje, connection);
		StringBuffer response = getResponse(connection, waitForResponse);

		return response.toString();
	}

	/**
	 * Establece comunicacion con la url proporcionada para enviar el mensaje
	 * 
	 * @param url     Url con la que se establece comunicacion
	 * @param mensaje mensaje que se envia al host
	 * @return respuesta de obtenida del host.
	 * @throws IOException
	 */
	public static String urlSendRecieve(String url, String mensaje) {
		return urlSendRecieve(url, mensaje, true);
	}

	/**
	 * Si waitForResponse es true, procesa la respuesta del servidor
	 * 
	 * @param connection
	 * @param waitForResponse
	 * @return respuesta del servidor
	 */
	private static StringBuffer getResponse(URLConnection connection, boolean waitForResponse) {
		StringBuffer response = new StringBuffer();
		if (waitForResponse) {
			try {
				response = readContent(connection.getInputStream());
				log.fine("respuesta url:" + response);
			} catch (IOException e) {
				readErrorStream((HttpURLConnection) connection);
			}
		}
		return response;
	}

	/**
	 * Lee el cuerpo de la respuesta en caso de error.
	 * 
	 * @param connection
	 */
	private static void readErrorStream(HttpURLConnection connection) {
		int statusCode = 200;
		String statusMessage = null;
		try {
			statusCode = connection.getResponseCode();
			statusMessage = connection.getResponseMessage();
		} catch (IOException e) {
			throw new ResponseException("Error al obtener la respuesta: " + e.getMessage());
		}
		StringBuffer response = readContent(connection.getErrorStream());
		throw new ResponseException("Error getting response from server: ", statusCode, statusMessage,
				response.toString());
	}

	/**
	 * Lee el cuerpo de la respuesta del servidor.
	 * 
	 * @param input
	 * @return
	 */
	private static StringBuffer readContent(InputStream input) {
		StringBuffer response = new StringBuffer();
		String linea;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(input));) {
			while ((linea = in.readLine()) != null) {
				response.append(linea);
			}
		} catch (IOException e) {
			throw new ResponseException("Error reading response body: " + e.getMessage());
		}
		return response;
	}

	/**
	 * Envia el mensaje al servidor
	 * 
	 * @param mensaje
	 * @param connection
	 */
	private static void sendRequest(String mensaje, URLConnection connection) {
		if (mensaje == null) {
			return;
		}
		try (DataOutputStream output = new DataOutputStream(connection.getOutputStream());) {
			output.writeBytes(mensaje);
		} catch (IOException e) {
			throw new RequestException("Error sending request: " + e.getMessage());
		}
	}
}
