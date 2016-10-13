package softwaresecurity.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.UsernameAlreadyExistsException;
import softwaresecurity.authenticator.Authenticator;
import softwaresecurity.authenticator.AuthenticatorInterface;

/**
 * Servlet implementation class CreateUser
 */
@WebServlet("/createUser")
public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ROOT = "root";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Check if an user is authenticated
		HttpSession session = request.getSession(true);
		// Set the MIME type for the response message
		response.setContentType("text/html");
		// Get a output writer to write the response message into the network socket
		PrintWriter out = response.getWriter();
		try{
			String authUser = session.getAttribute("USER").toString();
			AuthenticatorInterface authenticator = new Authenticator();
			if(!authUser.equals(ROOT)){
				out.println("<html><head><title>LoginError</title></head><body><p><h1>You are not authorized to do this operation</h1></p>"
						+ "<button class='btn btn-success' "
						+ "onclick=\"location.href = 'http://localhost:8080/SoftSec_Authenticator/home.html';\">Go Back</button>"
						+ "</body></html>");
			}else{
				authenticator.create_account(request.getParameter("firstnamesignup"), 
						request.getParameter("passwordsignup"), request.getParameter("passwordsignup_confirm"));
			}
		}catch (NullPointerException e){
			out.println("<html><head><title>LoginError</title></head><body><p><h1>You are not authorized to do this operation</h1></p>"
					+ "<button class='btn btn-success' "
					+ "onclick=\"location.href = 'http://localhost:8080/SoftSec_Authenticator/home.html';\">Go Back</button>"
					+ "</body></html>");
		}catch (UsernameAlreadyExistsException e){
			out.println("<html><head><title>LoginError</title></head><body><p><h1>Username Already In Use</h1></p>"
					+ "<button class='btn btn-success' "
					+ "onclick=\"location.href = 'http://localhost:8080/SoftSec_Authenticator/home.html';\">Go Back</button>"
					+ "</body></html>");
		}catch(Exception e){
			out.println("<html><head><title>LoginError</title></head><body><p><h1>Somethin went wrong. Please try again</h1></p>"
					+ "<button class='btn btn-success' "
					+ "onclick=\"location.href = 'http://localhost:8080/SoftSec_Authenticator/home.html';\">Go Back</button>"
					+ "</body></html>");
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
