package custom_exceptions;

/**
 * 
 * This class extends the Exception class.
 * This is a checked exception.
 * 
 */
public class BadScoreException extends Exception {
	
	public BadScoreException(String message) {
		super(message);
	}
	
	public BadScoreException() {
		super("missing score");
	}
	
	//Valid score <= 10
}
