package custom_exceptions;

/**
 * 
 * This class extends the Exception class.
 * This is a checked exception.
 * 
 */
public class BadDurationException extends Exception {
	
	public BadDurationException(String message) {
		super(message);
	}
	
	
	public BadDurationException() {
		super("missing duration");
	}
	
	//Check for duration from 30 to 300 mins?
	
}
