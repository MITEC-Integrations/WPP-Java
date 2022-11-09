package mx.com.mit.paygen.validation;

import static java.lang.String.format;

/**
 * Validador para cadenas
 */
public class StringValidation extends ChainValidation<String> {

	public StringValidation notNull() {
		push(SimpleValidation.notNull());
		return this;
	}

	private Validation<String> _lessThan(int size) {
		return SimpleValidation.from((s) -> s.length() <= size, format("must have less than %s chars.", size));
	}

	private Validation<String> _moreThan(int size) {
		return SimpleValidation.from((s) -> s.length() >= size, format("must have more than %s chars.", size));
	}

	public StringValidation lessThan(int size) {
		push(_lessThan(size));
		return this;
	}

	public StringValidation moreThan(int size) {
		push(_moreThan(size));
		return this;
	}

	public StringValidation between(int minSize, int maxSize) {
		push(moreThan(minSize).and(lessThan(maxSize)));
		return this;
	}

	public StringValidation contains(String c) {
		push(SimpleValidation.from((s) -> s.contains(c), format("must contain %s", c)));
		return this;
	}

	/**
	 * Indica si el valor es opcional
	 */
	public StringValidation optional(boolean flag) {
		super.optional(flag);
		return this;
	}
}