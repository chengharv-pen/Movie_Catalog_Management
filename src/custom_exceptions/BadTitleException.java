package custom_exceptions;

/**
 * 
 * This class extends the Exception class.
 * This is a checked exception.
 * 
 */
public class BadTitleException extends Exception {
	
	public BadTitleException(String message) {
		super(message);
	}
	
	public BadTitleException() {
		super("missing title");
	}
	
}
