package exceptions;

public class UsernameAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsernameAlreadyExistsException() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public UsernameAlreadyExistsException (String message){
		super(message);
	}
}
