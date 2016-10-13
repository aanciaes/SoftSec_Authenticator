package softwaresecurity.authenticator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.AccountLockedException;
import exceptions.AuthenticationError;
import exceptions.UndefinedAccountException;
import exceptions.UsernameAlreadyExistsException;

public class Authenticator implements AuthenticatorInterface{

	@Override
	public void create_account(String name, String pwd1, String pwd2) throws UsernameAlreadyExistsException {
		Connection conn = null;
		Statement stmt = null;
		name = name.toLowerCase();
		try {
			// Step 1: Create a database "Connection" object
			// For SqLite3
			conn = connectDB();

			// Step 2: Create a "Statement" object inside the "Connection"
			stmt = conn.createStatement();
			
			// Step 3: Execute a SQL SELECT query
			String sqlStr = "SELECT username FROM logins WHERE username = "
					+ "'" + name + "';";
				stmt.executeUpdate(sqlStr);
			ResultSet rset = stmt.executeQuery(sqlStr); // Send the query to the server
			
			if(!rset.isClosed()){
				throw new UsernameAlreadyExistsException();
			}
			
			else
				if(pwd1.equals(pwd2)){
					// Step 3: Execute a SQL SELECT query
					String sqlStr2 = "INSERT INTO logins (username, password) values ( '" + name + "','" + pwd1 + "');";
					stmt.executeUpdate(sqlStr2);
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
	public void deleteAccount(String name) throws UndefinedAccountException, AccountLockedException{
		Connection conn = null;
		Statement stmt = null;
		name = name.toLowerCase();
		try {
			// Step 1: Create a database "Connection" object
			// For SqLite3
			conn = connectDB();

			// Step 2: Create a "Statement" object inside the "Connection"
			stmt = conn.createStatement();

				// Step 3: Execute a SQL SELECT query
			String sqlStr = "SELECT isLocked FROM logins WHERE username = "
					+ "'" + name + "';";
				stmt.executeUpdate(sqlStr);
			ResultSet rset = stmt.executeQuery(sqlStr); // Send the query to the server
			if(rset.isClosed()){
				throw new UndefinedAccountException();
			}else{
				// Step 4: Process the query result
				boolean isLocked = rset.getBoolean("isLocked");
				if(!isLocked){
					throw new AccountLockedException();
				}else{
					String sqlStr2 = "delete FROM logins WHERE username = "
							+ "'" + name + "';";
					stmt.executeUpdate(sqlStr2);

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
	}

	@Override
	public Account get_account(String name) {
		Connection conn = null;
		Statement stmt = null;
		name = name.toLowerCase();
		try{
			// Step 1: Create a database "Connection" object
			// For SqLite3
			conn = connectDB();

			// Step 2: Create a "Statement" object inside the "Connection"
			stmt = conn.createStatement();

			String sqlStr = "SELECT * FROM logins WHERE username = "
					+ "'" + name + "';";

			ResultSet rset = stmt.executeQuery(sqlStr); // Send the query to the server
			if(rset.isClosed()){
				return null;
			}
			else
				return new Account(rset.getString("username"), rset.getString("password"));

		}
		catch (SQLException ex) {
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
	public void change_pwd(String name, String pwd1, String pwd2) throws UndefinedAccountException {
		Connection conn = null;
		Statement stmt = null;
		name = name.toLowerCase();
		try {
			// Step 1: Create a database "Connection" object
			// For SqLite3
			conn = connectDB();

			// Step 2: Create a "Statement" object inside the "Connection"
			stmt = conn.createStatement();

			String sqlStr = "SELECT * FROM logins WHERE username = "
					+ "'" + name + "';";

			ResultSet rset = stmt.executeQuery(sqlStr); // Send the query to the server
			if(rset.isClosed()){
				throw new UndefinedAccountException();
			}
			else
			if(pwd1.equals(pwd2)){
				// Step 3: Execute a SQL SELECT query
				sqlStr = "UPDATE logins set password = '" + pwd1+ "' WHERE username = '" + name + "');";
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
	public Account login(String name, String pwd)
			throws UndefinedAccountException, AccountLockedException, AuthenticationError {
		Connection conn = null;
		Statement stmt = null;
		name = name.toLowerCase();
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
