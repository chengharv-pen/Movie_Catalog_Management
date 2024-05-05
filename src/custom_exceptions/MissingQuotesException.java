package custom_exceptions;

/**
 * 
 * This class extends the Exception class.
 * This is a checked exception.
 * 
 */
public class MissingQuotesException extends Exception {
	
	public MissingQuotesException(String message) {
		super(message);
	}
	
	public MissingQuotesException() {
		super("missing quotes");
	}
	
}
