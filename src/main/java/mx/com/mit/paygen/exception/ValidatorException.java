package mx.com.mit.paygen.exception;

public class ValidatorException extends RuntimeException {

	private static final long serialVersionUID = -2817245199611490609L;

	public ValidatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidatorException(String message) {
		super(message);
	}
}
