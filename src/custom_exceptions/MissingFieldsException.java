package custom_exceptions;

/**
 * 
 * This class extends the Exception class.
 * This is a checked exception.
 * 
 */
public class MissingFieldsException extends Exception {
	
	public MissingFieldsException(String message) {
		super(message);
	}
	
	public MissingFieldsException() {
		super("missing fields");
	}
	
}
