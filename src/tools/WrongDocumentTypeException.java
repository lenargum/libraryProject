package tools;

/**
 * Exception which is being thrown when got
 * wrong document type.
 *
 * @author Ruslan Shakirov
 */
public class WrongDocumentTypeException extends RuntimeException {
	public WrongDocumentTypeException() {
	}

	public WrongDocumentTypeException(String message) {
		super(message);
	}
}
