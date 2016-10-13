package exceptions;

public class AccountLoggedInError extends Exception {

	private static final long serialVersionUID = 1L;

	public AccountLoggedInError () {
		super();
	}
	
	public AccountLoggedInError (String message){
		super(message);
	}

}