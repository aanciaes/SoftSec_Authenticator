package softwaresecurity.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import exceptions.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.AccountLockedException;
import exceptions.AccountLoggedInError;
import exceptions.UndefinedAccountException;
import softwaresecurity.authenticator.Authenticator;
import softwaresecurity.authenticator.AuthenticatorInterface;
/**
 * Created by MigMg on 10/10/2016.
 */


/*
        Falta testar

 */
@WebServlet("/deleteuser")
public class DeleteUser extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Set the MIME type for the response message
        response.setContentType("text/html");

        // Get a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();
        AuthenticatorInterface authenticator = new Authenticator();
        HttpSession session = request.getSession(true);
        try{
            //login
            String name = request.getParameter("firstnamesignup");

            String authUser = session.getAttribute("USER").toString();
            if(authUser.equals(name)){
            	out.println("<html><head><title>LoginError</title></head><body><p><h1>You can't be logged in to delete this account</h1></p>"
    					+ "<button class='btn btn-success' "
    					+ "onclick=\"location.href = 'https://localhost:8443/SoftSec_Authenticator/home.html';\">Go Back</button>"
    					+ "</body></html>");
            }
            else{
                authenticator.deleteAccount(request.getParameter("firstnamesignup"));
    			response.sendRedirect("https://localhost:8443/SoftSec_Authenticator/home.html");
                	
            }
        } catch (UndefinedAccountException ex) {
            out.println("<html><head><title>LoginError</title></head><body><p>This account doesn't exist</p>"
                    + "<button class='btn btn-success' "
                    + "onclick=\"location.href = 'https://localhost:8443/SoftSec_Authenticator/home.html';\">Go Back</button>"
                    + "</body></html>");
        }
        
        catch (AccountLockedException ex) {
            out.println("<html><head><title>LoginError</title></head><body><p>This account isn't locked. It should be locked if you want to delete it.</p>"
                    + "<button class='btn btn-success' "
                    + "onclick=\"location.href = 'https://localhost:8443/SoftSec_Authenticator/home.html';\">Go Back</button>"
                    + "</body></html>");
        }
        
        finally {
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
