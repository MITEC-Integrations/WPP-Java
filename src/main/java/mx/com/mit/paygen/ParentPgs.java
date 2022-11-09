package mx.com.mit.paygen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Setter;
import lombok.ToString;

/**
 * Contenedor del XML con identificador y cadena cifrada
 */
@XmlRootElement(name = "pgs")
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@ToString
final class ParentPgs {

	/**
	 * Identificador proporcionado por Pagos Online
	 */
	@XmlElement
	private String data0;

	
	/**
	 * Cadena cifrada en base64
	 */
	@XmlElement
	private String data;
}
