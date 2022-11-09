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
 * Contenedor XML del elemento business
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idCompany", "idBranch", "user", "pwd" })
@Builder
@Data
public class BusinessData {

	/**
	 * Identificador del Comercio.
	 */
	@XmlElement(name = "id_company")
	private String idCompany;

	/**
	 * Identificador de la Sucursal del Comercio.
	 */
	@XmlElement(name = "id_branch")
	private String idBranch;

	/**
	 * Usuario con el que se procesa la transaccion de cobro.
	 */
	@XmlElement
	private String user;

	/**
	 * Contraseña de acceso del usuario
	 */
	@XmlElement
	private String pwd;

	@Tolerate
	public BusinessData() {
	}
}
