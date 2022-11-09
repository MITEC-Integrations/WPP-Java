package mx.com.mit.paygen.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;

@XmlRootElement(name = "P_RESPONSE")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@ToString
public class GenUrlResponse {

	@XmlElement(name = "cd_response")
	private String cdResponse;
	
	@XmlElement(name ="nb_response")
	private String nbResponse;
	
	@XmlElement(name = "nb_url")
	private String nbUrl;
}
