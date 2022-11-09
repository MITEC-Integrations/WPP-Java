package mx.com.mit.paygen.models.validators;

import static org.junit.jupiter.api.Assertions.*;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.java.Log;
import mx.com.mit.paygen.models.UrlData;
import mx.com.mit.paygen.validation.ValidationResult;

@Log
public class UrlValidatorTest {

	public static EasyRandom generator = new EasyRandom();

	UrlValidator validator = new UrlValidator();
	UrlData data;

	@BeforeEach
	public void initUrl() {
		data = generator.nextObject(UrlData.class);
	}

	@Test
	public void thatAmountFails() {
		ValidationResult result = validator.test(data);
		log.info(result.getMesssage());
		assertTrue(!result.isvalid());
		assertTrue(result.getMesssage() != null && result.getMesssage().contains("amount"));
	}

	@Test
	void thatReferenceFails() {
		data.setReference(null);
		ValidationResult result = validator.test(data);
		log.info(result.getMesssage());
		assertTrue(!result.isvalid());
		assertTrue(result.getMesssage() != null && result.getMesssage().contains("reference"));

	}
}
