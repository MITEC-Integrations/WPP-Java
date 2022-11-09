package mx.com.mit.paygen.exception;

import java.util.Collections;
import java.util.List;

import mx.com.mit.paygen.models.ErrorResponse;

public class WPPException extends RuntimeException {

	private static final long serialVersionUID = -2817245199611490609L;
	private List<ErrorResponse> errors;

	public WPPException(String message, Throwable cause) {
		super(message, cause);
	}

	public WPPException(String message) {
		super(message);
	}

	public WPPException(String message, List<ErrorResponse> errors) {
		super(message);
		this.errors = errors;
	}

	public List<ErrorResponse> getErrors() {
		return Collections.unmodifiableList(errors);
	}

	@Override
	public String getMessage() {
		String message = super.getMessage();
		if (errors != null && !errors.isEmpty()) {
			message = message + " " + errors;
		}
		return message;
	}
}
