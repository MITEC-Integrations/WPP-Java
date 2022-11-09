package mx.com.mit.paygen.models;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * Contenedor XML del elemento url
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "reference", "amount", "moneda", "canal", "omitNotification", "promotions", "idPromotion",
		"stEmail", "expirationDate", "clientEmail", "prepaid" })
@Data
@Builder
public class UrlData {

	public static enum MonedaType {
		MXN, USD
	};

	/**
	 * Referencia única/irrepetible del comercio.
	 */
	@XmlElement
	private String reference;

	/**
	 * Importe a pagar con 2 decimales separado por un punto.
	 */
	@XmlElement
	@XmlJavaTypeAdapter(AmountTypeAdapter.class)
	private Double amount;

	/**
	 * Específica la divisa para la intención de cobro.
	 */
	@XmlElement
	private MonedaType moneda;

	/**
	 * 
	 */
	@XmlElement
	private final String canal = "W";

	/**
	 * Si el es valor 0, se enviará la notificación de cobro realizada al
	 * tarjetahabiente vía correo electrónico, con formato e imagen de Pagos Online
	 */
	@XmlElement(name = "omitir_notif_default")
	private Integer omitNotification;

	/**
	 * Indica las promociones a meses con las que se puede realizar el pago. La
	 * opción de pago de contado siempre estará disponible. Para forzar el pago a
	 * sólo contado, deberá indicarse sólo "C". El cobro a MSI solo puede realizarse
	 * con tarjeta de crédito (no se recibirá ninguna tarjeta de débito si no se
	 * agrega la opción de contado explícitamente). Valores posibles C,3,6,9,12
	 */
	@XmlElement(name = "promociones")
	private String promotions;

	/**
	 * Indica las promociones a meses configuradas por el administrador en el
	 * backoffice. Si se incluye este elemento no debera incluirse el elemento
	 * promociones
	 */
	@XmlElement(name = "id_promotion")
	private String idPromotion;

	/**
	 * Si el valor es 1, se solicita la captura del correo electronico en el
	 * formulario de pago.
	 */
	@XmlElement(name = "st_correo")
	private Integer stEmail;

	/**
	 * La intencion de cobro estara vigente hasta la fecha indicada. El valor por
	 * omision es de 3 meses a partir de la intencion de cobro.
	 */
	@XmlElement(name = "fh_vigencia")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date expirationDate;

	/**
	 * Valor que se mostrara en el campo "Correo Electronico" del formulario de
	 * pago.
	 */
	@XmlElement(name = "mail_cliente")
	private String clientEmail;

	/**
	 * Si el valor es 1, se solicitara la captura de la tarjeta de prepago
	 * previamente generada para el comercio
	 */
	@XmlElement(name = "prepago")
	private Integer prepaid; // 0 | 1

	@Tolerate
	public UrlData() {
	}

	public void setPromotions(String... promotion) {
		String promos = "";
		for (String promo : promotion) {
			promos += promo + ",";
		}
		this.promotions = promos.substring(0, promos.length() - 1);
	}

	private static class DateAdapter extends XmlAdapter<String, Date> {
		private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		@Override
		public String marshal(Date v) throws Exception {
			return sdf.format(v);
		}

		@Override
		public Date unmarshal(String v) throws Exception {
			return sdf.parse(v);
		}
	}

	private static class AmountTypeAdapter extends XmlAdapter<String, Double> {
		private static DecimalFormat df = new DecimalFormat("0.##");

		@Override
		public String marshal(Double v) throws Exception {
			return df.format(v);
		}

		@Override
		public Double unmarshal(String v) throws Exception {
			return df.parse(v).doubleValue();
		}
	}

}