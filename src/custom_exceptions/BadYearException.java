package custom_exceptions;

/**
 * 
 * This class extends the Exception class.
 * This is a checked exception.
 * 
 */
public class BadYearException extends Exception {
	
	public BadYearException(String message) {
		super(message);
	}
	
	public BadYearException() {
		super("missing year");
	}
	
	//valid year is int from 1900 to 1990
}
