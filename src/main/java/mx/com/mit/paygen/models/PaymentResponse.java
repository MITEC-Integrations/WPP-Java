package mx.com.mit.paygen.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Getter;
import lombok.ToString;

@XmlRootElement(name = "CENTEROFPAYMENTS")
@XmlAccessorType
@Getter
@ToString
public class PaymentResponse {

	private static class DateAdapter extends XmlAdapter<String, LocalDate> {
		private static DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yy");

		@Override
		public String marshal(LocalDate v) throws Exception {
			return v.format(sdf);
		}

		@Override
		public LocalDate unmarshal(String v) throws Exception {
			return LocalDate.parse(v, sdf);
		}
	}

	private static class TimeAdapter extends XmlAdapter<String, LocalTime> {
		private static DateTimeFormatter sdf = DateTimeFormatter.ofPattern("HH:mm:ss");

		@Override
		public String marshal(LocalTime v) throws Exception {
			return v.format(sdf);
		}

		@Override
		public LocalTime unmarshal(String v) throws Exception {
			return LocalTime.parse(v, sdf);
		}
	}

	@XmlElement
	private String reference;

	@XmlElement
	private String response;

	@XmlElement(name = "foliocpagos")
	private String folio;

	@XmlElement(name = "auth")
	private String authorization;

	@XmlElement(name = "cd_response")
	private String responseCode;

	@XmlElement(name = "cd_error")
	private String errorCode;

	@XmlElement(name = "nb_error")
	private String errorDescription;

	@XmlElement
	@XmlJavaTypeAdapter(TimeAdapter.class)
	private LocalTime time;

	@XmlElement
	@XmlJavaTypeAdapter(DateAdapter.class)
	private LocalDate date;

	@XmlElement(name = "nb_company")
	private String companyName;

	@XmlElement(name = "nb_merchant")
	private String merchantName;

}
