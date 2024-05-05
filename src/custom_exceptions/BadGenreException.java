package custom_exceptions;

/**
 * 
 * This class extends the Exception class.
 * This is a checked exception.
 * 
 */
public class BadGenreException extends Exception {
	
	public BadGenreException(String message) {
		super(message);
	}
	
	public BadGenreException() {
		super("missing genre");
	}
}
