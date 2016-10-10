package exceptions;

public class NotAuthorizedUserException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotAuthorizedUserException() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public NotAuthorizedUserException (String message){
		super(message);
	}
}

