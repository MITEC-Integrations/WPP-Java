package mx.com.mit.paygen.validation;

import java.util.function.Predicate;

import static java.lang.String.format;

/**
 * Validaciones Simples
 * 
 * @param <K> Tipo de objeto sobre el que aplica la validacion
 */
public class SimpleValidation<K> implements Validation<K> {

	private Predicate<K> predicate;
	private String onErrorMessage;

	public static <K> SimpleValidation<K> from(Predicate<K> predicate, String onErrorMessage) {
		return new SimpleValidation<K>(predicate, onErrorMessage);
	}

	private SimpleValidation(Predicate<K> predicate, String onErrorMessage) {
		this.predicate = predicate;
		this.onErrorMessage = onErrorMessage;
	}

	@Override
	public ValidationResult test(K param) {
		return predicate.test(param) ? ValidationResult.ok() : ValidationResult.fail(onErrorMessage);
	}

	public static <K> SimpleValidation<K> notNull() {
		return SimpleValidation.from((s) -> s != null, "must not be null.");
	}

	public static <K> SimpleValidation<K> equalTo(K value) {
		return SimpleValidation.from((s) -> !value.equals(s), format("must be equal to %s)", value));
	}

}