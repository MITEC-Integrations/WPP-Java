package mx.com.mit.paygen.exception;

public class AesException extends RuntimeException {

	private static final long serialVersionUID = -2817245199611490609L;

	public AesException(String message, Throwable cause) {
		super(message, cause);
	}

	public AesException(String message) {
		super(message);
	}
}
