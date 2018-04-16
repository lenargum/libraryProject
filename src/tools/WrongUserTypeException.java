package tools;

/**
 * Exception which is being thrown when got
 * wrong user type.
 *
 * @author Ruslan Shakirov
 */
public class WrongUserTypeException extends RuntimeException {
	public WrongUserTypeException() {
		super();
	}

	public WrongUserTypeException(String message) {
		super(message);
	}
}
