package exceptions;

public class PasswordsDontMatchException extends Exception {

	private static final long serialVersionUID = 1L;

	public PasswordsDontMatchException () {
		super();
	}
	
	public PasswordsDontMatchException (String message){
		super(message);
	}
}
