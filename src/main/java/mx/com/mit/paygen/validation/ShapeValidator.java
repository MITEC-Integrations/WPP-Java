package mx.com.mit.paygen.validation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import mx.com.mit.paygen.exception.ValidatorException;

import static java.lang.String.format;

/**
 * Validaciones para objetos complejos.
 * 
 * @param <K> Tipo de objeto que se validara.
 */
public class ShapeValidator<K> implements Validation<K> {

	@SuppressWarnings("rawtypes")
	private HashMap<String, Validation> fieldsValidators = new HashMap<>();

	private boolean optional;

	/**
	 * Ejecuta el proceso de validacion despues de configurado
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ValidationResult test(K param) {
		ValidationResult result = ValidationResult.ok();

		if (Objects.isNull(param)) {
			return optional ? result : ValidationResult.fail(format("%s must not be null", param));
		}

		for (Map.Entry<String, Validation> entry : fieldsValidators.entrySet()) {
			Object value = getActualValue(entry.getKey(), param);
			result = entry.getValue().test(value);
			if (!result.isvalid()) {
				return ValidationResult.fail(format("%s %s", entry.getKey(), result.getMesssage()));
			}
		}
		return result;
	}

	/**
	 * Indica si la validacion es opcional. Si flag es true, y el valor/objeto es
	 * nulo, la validacion es correcta.
	 * 
	 * @param flag
	 * @return
	 */
	public ShapeValidator<K> optional(boolean flag) {
		this.optional = flag;
		return this;
	}

	/**
	 * Configura las validaciones para la clase <K>
	 * 
	 * @param fieldName  nombre del campo/atributo a validar
	 * @param validation Validacion que se ejecuta sobre el atributo
	 * @return
	 */
	public ShapeValidator<K> validateWhen(String fieldName, Validation<?> validation) {
		fieldsValidators.put(fieldName, validation);
		return this;
	}

	private Object getActualValue(String fieldName, K object) {
		Objects.requireNonNull(fieldName);

		Field field = null;
		try {
			field = object.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new ValidatorException(e.getMessage());
		}

		if (Modifier.isStatic(field.getModifiers())) {
			return null;
		}

		if (!field.canAccess(object)) {
			field.setAccessible(true);
		}

		try {
			return field.get(object);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new ValidatorException(e.getMessage());
		}
	}
}
