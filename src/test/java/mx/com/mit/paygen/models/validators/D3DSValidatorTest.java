package mx.com.mit.paygen.models.validators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mx.com.mit.paygen.models.D3DSData;
import mx.com.mit.paygen.validation.ValidationResult;

public class D3DSValidatorTest {

	public static EasyRandom generator = new EasyRandom();

	D3DSValidator validator = new D3DSValidator();
	D3DSData data;

	@BeforeEach
	public void initUrl() {
		data = generator.nextObject(D3DSData.class);
	}

	@Test
	public void thatZipCodeFails() {
		data.setZipCode("zipCode89012");
		ValidationResult result = validator.test(data);
		assertTrue(!result.isvalid());
		assertTrue(result.getMesssage() != null && result.getMesssage().contains("zipCode"));
	}

	@Test
	public void thatIsValid() {
		data.setZipCode("12345");
		data.setPhone("12345678901234567890");
		data.setCountryCode("MEX");
		data.setState("PU");
		ValidationResult result = validator.test(data);
		assertTrue(result.isvalid());
	}
}
