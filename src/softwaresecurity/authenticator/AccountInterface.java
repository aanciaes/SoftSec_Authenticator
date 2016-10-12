package softwaresecurity.authenticator;

public interface AccountInterface {

	/**
	 * Changes current password to given password
	 * @param pwd New Password
	 */
	void changePWD ( String pwd);
	
	/**
	 * Change the lock state
	 * @param locked_in lock state to be changed to
	 */
	void changeLocked (boolean locked_in);
	
	/**
	 * Change current logged in state
	 * @param logged_in logg state to be change to
	 */
	void changeLogin (boolean logged_in);
	
	/**
	 * Return the account name
	 * @return The account Name
	 */
	String getAccountName ();
	
	/**
	 * Return the password
	 * @return password
	 */
	String getPassword ();
	
	/**
	 * Returns true if this account is currently looged in
	 * @return true if this account is currently logged in
	 */
	boolean isLoggedIn ();
	
	/**
	 * Returns true if the account is currently locked
	 * @return true if the account is currently locked
	 */
	boolean isLocked();
}
