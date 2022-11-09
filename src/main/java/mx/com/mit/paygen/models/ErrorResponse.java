package mx.com.mit.paygen.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Builder;
import lombok.Data;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@Builder
public class ErrorResponse {

	@XmlElement
	private String defaultMessage;

	@XmlElement
	private String field;

	@XmlElement
	private String rejectedvalue;
}
