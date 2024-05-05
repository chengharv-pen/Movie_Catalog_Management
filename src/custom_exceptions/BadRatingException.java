package custom_exceptions;

/**
 * 
 * This class extends the Exception class.
 * This is a checked exception.
 * 
 */
public class BadRatingException extends Exception {

	public BadRatingException(String message) {
		super(message);
	}
	
	public BadRatingException() {
		super("missing rating");
	}
}
