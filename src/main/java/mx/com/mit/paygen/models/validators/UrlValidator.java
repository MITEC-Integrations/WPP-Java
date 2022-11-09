package mx.com.mit.paygen.models.validators;

import mx.com.mit.paygen.models.UrlData;
import mx.com.mit.paygen.validation.NumberValidation;
import mx.com.mit.paygen.validation.ShapeValidator;
import mx.com.mit.paygen.validation.SimpleValidation;
import mx.com.mit.paygen.validation.StringValidation;
import mx.com.mit.paygen.validation.Validation;
import mx.com.mit.paygen.validation.ValidationResult;

/**
 * Validadores basicos para UrlData
 */
public class UrlValidator implements Validation<UrlData> {
	private ShapeValidator<UrlData> businessValidator;

	public UrlValidator() {
		businessValidator = new ShapeValidator<UrlData>()
				.validateWhen("reference", new StringValidation().notNull().between(1, 50))
				.validateWhen("amount", new NumberValidation().notNull().between(1, 11))
				.validateWhen("moneda", SimpleValidation.notNull())
				.validateWhen("omitNotification", SimpleValidation.equalTo(1).or(SimpleValidation.equalTo(0)))
				.validateWhen("promotions", new StringValidation().optional(true).between(1, 40))
				.validateWhen("stEmail",
						new NumberValidation().optional(true).equalTo(1).or(SimpleValidation.equalTo(0)))
//				.validateWhen("expirationDate", SimpleValidation )
				.validateWhen("clientEmail", new StringValidation().optional(true).between(1, 100)).validateWhen(
						"prepaid", new NumberValidation().optional(true).equalTo(1).or(SimpleValidation.equalTo(0)));
	}

	@Override
	public ValidationResult test(UrlData param) {
		return businessValidator.test(param);
	}

}
