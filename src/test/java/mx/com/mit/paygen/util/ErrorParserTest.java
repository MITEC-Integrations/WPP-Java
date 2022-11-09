package mx.com.mit.paygen.util;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mx.com.mit.paygen.models.ErrorResponse;

public class ErrorParserTest {

	@Test
	public void thatParsesErrors() {
		String response = "[{\"defaultMessage\":\"El elemento data0 es incorrecto\",\"field\":\"data0\",\"rejectedvalue\":\"cYDMpMeOmLMpVOJkywvA=\"}"
				+ ", {\"defaultMessage\":\"El elemento data0 es incorrecto\",\"field\":\"data0\",\"rejectedvalue\":\"0\"}]";
		List<ErrorResponse> errors = ErrorResponseParser.parse(response);
		Assertions.assertTrue(!errors.isEmpty());
	}
}
