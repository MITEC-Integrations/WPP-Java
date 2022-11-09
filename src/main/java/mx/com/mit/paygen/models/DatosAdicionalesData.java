package mx.com.mit.paygen.models;

import java.util.LinkedHashSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * Contenedor XML para el elemento datos_adicionales. Si este elemento esta
 * presente, se incluiran los elementos hijos data en el formulario de pago y
 * seran devueltos en la respuesta.
 */
@XmlRootElement(name = "datos_adicionales")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class DatosAdicionalesData {

	@XmlRootElement
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "id", "display", "label", "value" })
	@Data
	@Builder
	public static class DataItem {

		@Tolerate
		public DataItem() {
		}

		/**
		 * Identificador del dato adicional
		 */
		@XmlAttribute
		private Integer id;

		/**
		 * Si el valor es <b>true</b> el dato se muestra en el formulario de pago
		 */
		@XmlAttribute
		private Boolean display;

		/**
		 * Etiqueta del dato adicional
		 */
		@XmlElement
		private String label;

		/**
		 * Valor del dato adicional
		 */
		@XmlElement
		private String value;
	}

	/**
	 * Si este campo cuenta con un elemento <data> donde el <label> tenga valor
	 * <b>PRINCIPAL</b>, se usara el valor del nodo <value> para identificar al
	 * usuario y presentarle la opción de Guardar Tarjeta. <b>Importante</b> Todas
	 * las ligas asociadas a un mismo cliente, deberan enviar el mismo <value>, de
	 * esta forma es el comercio quien mantiene el control del catalogo de sus
	 * usuarios.<br>
	 * <code>
	 * <data id="1" display="false">
	 *	    <label>PRINCIPAL</label>
	 *	    <value>ID_CLIENTE_DEL_COMERCIO</value>
	 *	</data>
	 *	</code>
	 */
	@XmlElement
	private LinkedHashSet<DataItem> data;

	public void append(DataItem aditionalData) {
		if (data == null) {
			data = new LinkedHashSet<DataItem>();
		}
		data.add(aditionalData);
	}
}
