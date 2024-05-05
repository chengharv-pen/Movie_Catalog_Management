package custom_exceptions;

/**
 * 
 * This class extends the Exception class.
 * This is a checked exception.
 * 
 */
public class ExcessFieldsException extends Exception {
	
	public ExcessFieldsException(String message) {
		super(message);
	}
	
	public ExcessFieldsException() {
		super("excess fields");
	}
	
}
