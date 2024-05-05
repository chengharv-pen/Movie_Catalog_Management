package custom_exceptions;

/**
 * 
 * This class extends the Exception class.
 * This is a checked exception.
 * 
 */
public class BadNameException extends Exception {
	
	public BadNameException(String message) {
		super(message);
	}
	
	public BadNameException() {
		super("missing name(s)");
	}
}
