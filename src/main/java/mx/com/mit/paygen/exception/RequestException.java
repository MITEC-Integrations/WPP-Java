package mx.com.mit.paygen.exception;

public class RequestException extends RuntimeException {

	private static final long serialVersionUID = 8988275421197294719L;

	public RequestException(String message) {
		super(message);
	}
}
