package softwaresecurity.authenticator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.AccountLockedException;
import exceptions.AuthenticationError;
import exceptions.UndefinedAccountException;

public class Authenticator implements AuthenticatorInterface{

	@Override
	public void create_account(String name, String pwd1, String pwd2) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Step 1: Create a database "Connection" object
			// For SqLite3
			conn = connectDB();

			// Step 2: Create a "Statement" object inside the "Connection"
			stmt = conn.createStatement();

			if(pwd1.equals(pwd2)){
				// Step 3: Execute a SQL SELECT query
				String sqlStr = "INSERT INTO logins (username, password) values ( '" + name + "','" + pwd1 + "');";
				stmt.executeUpdate(sqlStr);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				// Step 5: Close the Statement and Connection
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAccount(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public Account get_account(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void change_pwd(String name, String pwd1, String pwd2) {
		// TODO Auto-generated method stub

	}

	@Override
	public Account login(String name, String pwd)
			throws UndefinedAccountException, AccountLockedException, AuthenticationError {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Step 1: Create a database "Connection" object
			// For SqLite3
			conn = connectDB();

			// Step 2: Create a "Statement" object inside the "Connection"
			stmt = conn.createStatement();

			// Step 3: Execute a SQL SELECT query
			String sqlStr = "SELECT password, isLocked FROM logins WHERE username = "
					+ "'" + name + "';";

			ResultSet rset = stmt.executeQuery(sqlStr); // Send the query to the server
			if(rset.isClosed()){
				throw new UndefinedAccountException();
			}else{
				// Step 4: Process the query result
				String password = rset.getString("password");
				boolean isLocked = rset.getBoolean("isLocked");
				if(isLocked){
					throw new AccountLockedException();
				}else{
					if(pwd.equals(password))
						return new Account (name, pwd);
					else
						throw new AuthenticationError();
				}
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				// Step 5: Close the Statement and Connection
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void logout(Account a) {
		// TODO Auto-generated method stub

	}

	/**
	 * Creates a connection to database
	 * @return The connection
	 * @throws SQLException
	 */
	private Connection connectDB () throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:/home/miguel/java-workspace/SoftSec_Authenticator/authenticator.db");  // <<== Check
	}
}
