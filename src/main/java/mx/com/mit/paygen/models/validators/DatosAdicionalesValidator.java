package mx.com.mit.paygen.models.validators;

import mx.com.mit.paygen.models.DatosAdicionalesData;
import mx.com.mit.paygen.validation.NumberValidation;
import mx.com.mit.paygen.validation.ShapeValidator;
import mx.com.mit.paygen.validation.SimpleValidation;
import mx.com.mit.paygen.validation.StringValidation;
import mx.com.mit.paygen.validation.Validation;
import mx.com.mit.paygen.validation.ValidationResult;

/**
 * Validadores basicos para DatosAdicionalesData
 */
public class DatosAdicionalesValidator implements Validation<DatosAdicionalesData> {
	private ShapeValidator<DatosAdicionalesData.DataItem> businessValidator;

	public DatosAdicionalesValidator() {
		businessValidator = new ShapeValidator<DatosAdicionalesData.DataItem>()
				.validateWhen("id", new NumberValidation().notNull())
				.validateWhen("display", SimpleValidation.notNull())
				.validateWhen("label", new StringValidation().notNull().between(1, 30))
				.validateWhen("value", new StringValidation().notNull().between(1, 100));
	}

	@Override
	public ValidationResult test(DatosAdicionalesData param) {
		ValidationResult result = ValidationResult.ok();
		if (param == null) {
			return result;
		}
		if (param.getData() == null || param.getData().size() == 0) {
			return ValidationResult.fail("must not be empty");
		}

		for (DatosAdicionalesData.DataItem item : param.getData()) {
			result = businessValidator.test(item);
			if (!result.isvalid()) {
				break;
			}
		}
		return result;
	}

}
