package mx.com.mit.paygen.models.validators;

import mx.com.mit.paygen.models.PaymentData;
import mx.com.mit.paygen.validation.ShapeValidator;
import mx.com.mit.paygen.validation.Validation;
import mx.com.mit.paygen.validation.ValidationResult;

/**
 * Validadores basicos para PaymentData
 */
public class PaymentValidator implements Validation<PaymentData> {
	private ShapeValidator<PaymentData> businessValidator;

	public PaymentValidator() {
		businessValidator = new ShapeValidator<PaymentData>().validateWhen("business", new BusinessValidator())
				.validateWhen("url", new UrlValidator()).validateWhen("data3ds", new D3DSValidator())
				.validateWhen("additionalData", new DatosAdicionalesValidator());
	}

	@Override
	public ValidationResult test(PaymentData param) {
		return businessValidator.test(param);
	}

}
