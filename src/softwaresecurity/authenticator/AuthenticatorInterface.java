package softwaresecurity.authenticator;

import exceptions.AccountLockedException;
import exceptions.AuthenticationError;
import exceptions.PasswordsDontMatchException;
import exceptions.UndefinedAccountException;
import exceptions.UsernameAlreadyExistsException;

public interface AuthenticatorInterface {
	
	/**
	 * Creates a new account object
	 * @param name The account name (must be unique)
	 * @param pwd1 the password
	 * @param pwd2 the password
	 * @pre pwd1 == pwd2
	 */
	void create_account (String name, String pwd1, String pwd2) throws UsernameAlreadyExistsException;
	
	/**
	 * Deletes an exiting account
	 * @param name Name of the account to be deleted
	 * @pre The account must be locked
	 * @pre The account cannot be logged in
	 */
	void deleteAccount (String name) throws UndefinedAccountException, AccountLockedException;
	
	/**
	 * Returns a clone (readonly) of an existing account object
	 * @param name The name of the account to be returned
	 * @return An account
	 */
	Account get_account (String name);
	
	/**
	 * Changes the password of the account name to pwd1
	 * @param name Name of the account
	 * @param pwd1 password1
	 * @param pwd2 password2
	 * @pre name must identify an created account
	 * @pre pwd1 == pwd2
	 */
	void change_pwd (String name, String pwd1, String pwd2) throws UndefinedAccountException, PasswordsDontMatchException;
	
	/**
	 * Authenticates the caller, given name and password
	 * @param name The name of the account
	 * @param pwd Password
	 * @return 
	 * @throws UndefinedAccountException If name does not define a created account
	 * @throws AccountLockedException If the account name is locked
	 */
	Account login (String name, String pwd) throws UndefinedAccountException, AccountLockedException, AuthenticationError;
	
	/**
	 * Logs of the account
	 * @param a
	 */
	void logout (Account a);

	String getRole(String username) throws UndefinedAccountException;

}
