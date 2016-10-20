package softwaresecurity.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.AccountLockedException;
import exceptions.AuthenticationError;
import exceptions.UndefinedAccountException;
import softwaresecurity.authenticator.Account;
import softwaresecurity.authenticator.Authenticator;
import softwaresecurity.authenticator.AuthenticatorInterface;
import softwaresecurity.encryptionAlgorithm.AESencrp;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
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
		AuthenticatorInterface authenticator = new Authenticator();
		try{		
			AESencrp enc = new AESencrp();
			String pwd = enc.encrypt(request.getParameter("password"));
			//login
			Account authUser = authenticator.login(request.getParameter("username"), pwd);
			//Create HTTP session and set attributes of logged user 
			HttpSession session = request.getSession(true);
			session.setAttribute("USER", authUser.getAccountName());
			session.setAttribute("PWD", authUser.getPassword());

			//redirect to home page
			response.sendRedirect("https://localhost:8443/SoftSec_Authenticator/home.html");

		} catch (UndefinedAccountException | AccountLockedException | AuthenticationError ex) {
			out.println("<html><head><title>LoginError</title></head><body><p>Login Failed</p>"
					+ "<button class='btn btn-success' "
					+ "onclick=\"location.href = 'https://localhost:8443/SoftSec_Authenticator/login.html';\">Go Back</button>"
					+ "</body></html>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.close();
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
