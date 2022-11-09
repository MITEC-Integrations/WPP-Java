package mx.com.mit.paygen.exception;

public class ResponseException extends RuntimeException {

	private static final long serialVersionUID = 8988275421197294719L;
	private Integer statusCode;
	private String statusMessage;
	private String errorBody;

	public ResponseException(String message) {
		super(message);
	}

	public ResponseException(String message, Integer statusCode, String statusMessage, String errorBody) {
		super(message);
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.errorBody = errorBody;
	}

	@Override
	public String getMessage() {
		String msg = super.getMessage() + (statusCode == null ? "" : " " + statusCode)
				+ (statusMessage == null ? "" : " " + statusMessage) + (errorBody == null ? "" : "\n" + errorBody);
		return msg;
	}

	public String getErrorBody() {
		return errorBody;
	}
}
