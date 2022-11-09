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
 * Contenedor xml para el elemento data3ds
 *
 */
/**
 * @author Alejandro Pardo
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "email", "phone", "address", "city", "state", "zipCode", "countryCode" })
@Data
@Builder
public class D3DSData {

	/**
	 * Correo electronico del tarjetahabiente.
	 */
	@XmlElement(name = "ml")
	private String email;

	/**
	 * Numero telefonico del tarjetahabiente.
	 */
	@XmlElement(name = "cl")
	private String phone;

	/**
	 * Domicilio del tarjetahabiente.
	 */
	@XmlElement(name = "dir")
	private String address;

	/**
	 * Ciudad del tarjeta habiente
	 */
	@XmlElement(name = "cd")
	private String city;

	/**
	 * Codigo en @see <a href="https://es.wikipedia.org/wiki/ISO_3166-2:MX">ISO
	 * 3166-2</a> del estado del domicilio tarjetahabiente.
	 */
	@XmlElement(name = "est")
	private String state;

	/**
	 * Codigo postal del domicilio del tarjetahabiente.
	 */
	@XmlElement(name = "cp")
	private String zipCode;

	/**
	 * Codigo en @see <a href="https://es.wikipedia.org/wiki/ISO_3166-1">ISO
	 * 3166-1</a> del pais del domicilio tarjetahabiente.
	 */
	@XmlElement(name = "idc")
	private String countryCode;

	@Tolerate
	public D3DSData() {
	}

}
