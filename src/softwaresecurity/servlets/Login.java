package softwaresecurity.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set the MIME type for the response message
		response.setContentType("text/html");
		// Get a output writer to write the response message into the network socket
		PrintWriter out = response.getWriter();

		Connection conn = null;
		Statement stmt = null;
		try {
			// Step 1: Create a database "Connection" object
			// For MySQL
			conn = DriverManager.getConnection(
					"jdbc:sqlite:/home/miguel/sqliteDatabases/autheticator.db");  // <<== Check
			// For MS Access
			// conn = DriverManager.getConnection("jdbc:odbc:ebookshopODBC");

			// Step 2: Create a "Statement" object inside the "Connection"
			stmt = conn.createStatement();

			// Step 3: Execute a SQL SELECT query
			String sqlStr = "SELECT password FROM logins WHERE username = "
					+ "'" + request.getParameter("username") + "';";

			// Print an HTML page as output of query
			out.println("<html><head><title>Login</title></head><body>");
			ResultSet rset = stmt.executeQuery(sqlStr); // Send the query to the server
			
			if(rset.isClosed()){
				out.println("No such user");
			}else{
				// Step 4: Process the query result
				String password = rset.getString("password");
				if(request.getParameter("password").equals(password))
					out.println("<p>Login Successful<p>");
				else
					out.println("<p>Login Failed<p>");
				out.println("</body></html>");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			out.close();
			try {
				// Step 5: Close the Statement and Connection
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
