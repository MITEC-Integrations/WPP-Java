package mx.com.mit.paygen.models.validators;

import mx.com.mit.paygen.models.BusinessData;
import mx.com.mit.paygen.validation.ShapeValidator;
import mx.com.mit.paygen.validation.StringValidation;
import mx.com.mit.paygen.validation.Validation;
import mx.com.mit.paygen.validation.ValidationResult;

/**
 * Validadores basicos para BusinessData
 */
public class BusinessValidator implements Validation<BusinessData> {
	private ShapeValidator<BusinessData> businessValidator;

	public BusinessValidator() {
		businessValidator = new ShapeValidator<BusinessData>()
				.validateWhen("idCompany", new StringValidation().between(4, 4))
				.validateWhen("idBranch", new StringValidation().between(1, 11))
				.validateWhen("user", new StringValidation().between(9, 11))
				.validateWhen("pwd", new StringValidation().between(1, 80));
	}

	@Override
	public ValidationResult test(BusinessData param) {
		return businessValidator.test(param);
	}

}
