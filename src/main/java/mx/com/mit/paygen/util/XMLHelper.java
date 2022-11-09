package mx.com.mit.paygen.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Clase que convierte los objetos a XML y viceversa
 */
public class XMLHelper {

	/**
	 * Convierte el objeto T a su representacion xml
	 * 
	 * @param <T>         tipo de objeto a convertir
	 * @param objToEncode Objeto a convertir
	 * @param encoding    Tipo de codificacion de la salida XML. Por default es
	 *                    UTF8.
	 * @return representacion xml del objeto
	 * @throws JAXBException Si ocurre algun error inesperado durante la
	 *                       serializacion
	 * @throws IOException   si no se puede escribir la salida.
	 */
	public static <T> String format(T objToEncode, String encoding) throws JAXBException, IOException {
		return format(objToEncode, encoding, Boolean.TRUE);
	}

	/**
	 * Convierte el objeto T a su representacion xml
	 * 
	 * @param <T>          tipo de objeto a convertir
	 * @param objToEncode  Objeto a convertir
	 * @param encoding     Tipo de codificacion de la salida XML. Por default es
	 *                     UTF8.
	 * @param formatString true si la cadena esta formateada; en caso contrario
	 *                     false.
	 * @return representacion xml del objeto
	 * @throws JAXBException Si ocurre algun error inesperado durante la
	 *                       serializacion
	 * @throws IOException   si no se puede escribir la salida.
	 */
	public static <T> String format(T objToEncode, String encoding, boolean formatString)
			throws JAXBException, IOException {
		StringWriter writeOut = new StringWriter();
		JAXBContext jc = JAXBContext.newInstance(objToEncode.getClass());
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_ENCODING, encoding);
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatString);
		m.marshal(objToEncode, writeOut);
		String encodedMessage = writeOut.toString();
		writeOut.close();
		m = null;
		jc = null;
		writeOut = null;
		return encodedMessage;
	}

	/**
	 * Convierte la cadena toDecode a su objeto representativo
	 * 
	 * @param <T>          Tipo de objeto al que se convierte.
	 * @param toDecode     cadena xml a deserealizar
	 * @param classToBound elemento principal al que se deserealizara el xml
	 * @return Objeto representativo de la cadena xml
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parse(String toDecode, Class<T> classToBound) throws JAXBException {
		StringReader reader = new StringReader(toDecode);
		JAXBContext jc = JAXBContext.newInstance(classToBound);

		XMLInputFactory xif = XMLInputFactory.newInstance();
		xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
		xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);

		XMLStreamReader xsr = null;
		try {
			xsr = xif.createXMLStreamReader(reader);
		} catch (XMLStreamException e) {
			throw new JAXBException(e.getMessage(), e.getCause());
		}
		Unmarshaller u = jc.createUnmarshaller();
		Object element = u.unmarshal(xsr);
		reader.close();
		reader = null;
		u = null;
		jc = null;
		return (T) element;
	}
}
