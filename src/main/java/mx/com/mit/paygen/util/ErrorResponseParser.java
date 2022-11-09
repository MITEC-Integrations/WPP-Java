package mx.com.mit.paygen.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mx.com.mit.paygen.models.ErrorResponse;

/**
 * Procesa los errores del servidor y los convierte en una lista de
 * ErrorResponse
 */
public class ErrorResponseParser {

	public static List<ErrorResponse> parse(String message) {
		message = message.replace("[{", "").replace("}]", "");
		String[] objs = message.split("\\},\\s*\\{");

		ArrayList<ErrorResponse> errors = new ArrayList<>();
		Stream.of(objs).forEach((item) -> {
			Map<String, String> map = Stream.of(item.split(",")).map(s -> s.split(":"))
					.collect(Collectors.toMap(s -> s[0].replaceAll("\"", ""), s -> s[1].replaceAll("\"", "")));

			errors.add(ErrorResponse.builder().defaultMessage(map.get("defaultMessage")).field(map.get("field"))
					.rejectedvalue(map.get("rejectedvalue")).build());
		});
		return errors;
	}

}
