package mx.com.mit.paygen.models.validators;

import mx.com.mit.paygen.models.D3DSData;
import mx.com.mit.paygen.validation.ShapeValidator;
import mx.com.mit.paygen.validation.StringValidation;
import mx.com.mit.paygen.validation.Validation;
import mx.com.mit.paygen.validation.ValidationResult;

/**
 * Validadores basicos para D3DSData
 */
public class D3DSValidator implements Validation<D3DSData> {
	private ShapeValidator<D3DSData> businessValidator;

	public D3DSValidator() {
		businessValidator = new ShapeValidator<D3DSData>().optional(true)
				.validateWhen("email", new StringValidation().notNull().between(1, 100))
				.validateWhen("phone", new StringValidation().notNull().between(1, 20))
				.validateWhen("address", new StringValidation().notNull().between(1, 60))
				.validateWhen("city", new StringValidation().notNull().between(1, 30))
				.validateWhen("state", new StringValidation().notNull().between(2, 2))
				.validateWhen("zipCode", new StringValidation().notNull().between(1, 10))
				.validateWhen("countryCode", new StringValidation().notNull().between(3, 3));
	}

	@Override
	public ValidationResult test(D3DSData param) {
		return businessValidator.test(param);
	}

}
