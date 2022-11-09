package mx.com.mit.paygen.validation;

import java.util.LinkedList;
import static java.lang.String.format;

/**
 * Ejecuta las validaciones en orden de insercion hasta que encuentra una que
 * falla.
 * 
 * @param <K>
 */
public abstract class ChainValidation<K> implements Validation<K> {

	private LinkedList<Validation<K>> queue = new LinkedList<>();
	private boolean optional;

	@Override
	public ValidationResult test(K param) {
		if (param == null) {
			return optional ? ValidationResult.ok() : ValidationResult.fail(format(" %s must not be null,", param));
		}
		return testNext(param);
	}

	private ValidationResult testNext(K param) {
		if (queue.isEmpty()) {
			return ValidationResult.ok();
		}

		ValidationResult result = queue.poll().test(param);
		return result.isvalid() ? testNext(param) : result;
	}

	public ChainValidation<K> optional(boolean flag) {
		this.optional = flag;
		return this;
	}

	/**
	 * Encola las validaciones a ejecutar.
	 * 
	 * @param validation
	 */
	protected final void push(Validation<K> validation) {
		queue.push(validation);
	}

}