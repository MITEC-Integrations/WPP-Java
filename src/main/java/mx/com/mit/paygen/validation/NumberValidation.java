package mx.com.mit.paygen.validation;

import static java.lang.String.format;

/**
 * Validaciones basicas para objetos numericos.
 */
public class NumberValidation extends ChainValidation<Number> {

	public NumberValidation notNull() {
		push(SimpleValidation.notNull());
		return this;
	}

	public NumberValidation equalTo(Number number) {
		push(SimpleValidation.equalTo(number));
		return this;
	}

	private Validation<Number> _lessThan(int size) {
		return SimpleValidation.from((s) -> s.toString().length() <= size,
				format("must have less than %s chars.", size));
	}

	private Validation<Number> _moreThan(int size) {
		return SimpleValidation.from((s) -> s.toString().length() >= size,
				format("must have more than %s chars.", size));
	}

	public NumberValidation lessThan(int size) {
		push(_lessThan(size));
		return this;
	}

	public NumberValidation moreThan(int size) {
		push(_moreThan(size));
		return this;
	}

	public NumberValidation between(int minSize, int maxSize) {
		push(moreThan(minSize).and(lessThan(maxSize)));
		return this;
	}

	public NumberValidation optional(boolean flag) {
		super.optional(flag);
		return this;
	}
}