package mx.com.mit.paygen.models.validators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mx.com.mit.paygen.models.DatosAdicionalesData;
import mx.com.mit.paygen.validation.ValidationResult;

public class DatosAdicionalesValidatorTest {

	public static EasyRandom generator = new EasyRandom();

	DatosAdicionalesValidator validator = new DatosAdicionalesValidator();
	DatosAdicionalesData data;

	@BeforeEach
	public void initUrl() {
		data = generator.nextObject(DatosAdicionalesData.class);
	}

	@Test
	public void thatIsInvalid() {
		data.getData().stream().findAny().get().setDisplay(null);
		ValidationResult result = validator.test(data);
		assertTrue(!result.isvalid());
	}

	@Test
	public void thatIsValid() {
		ValidationResult result = validator.test(data);
		assertTrue(result.isvalid());
	}
}
