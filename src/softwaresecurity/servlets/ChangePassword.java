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
import exceptions.*;
import softwaresecurity.authenticator.Authenticator;
import softwaresecurity.authenticator.AuthenticatorInterface;
/**
 * Created by MigMg on 11/10/2016.
 */


@WebServlet("/changepwd")
public class ChangePassword extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePassword() {
        super();
        // TODO Auto-generated constructor stub
    }




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the MIME type for the response message
        response.setContentType("text/html");
        // Get a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();
        AuthenticatorInterface authenticator = new Authenticator();
        HttpSession session = request.getSession(true);

        try{
            //login
            authenticator.change_pwd(session.getAttribute("USER").toString(), request.getParameter("password1"), request.getParameter("password2"));
            //redirect to home page
            response.sendRedirect("http://localhost:8080/SoftSec_Authenticator/home.html");
        }   
         catch(PasswordsDontMatchException ex){
        	 out.println("<html><head><title>LoginError</title></head><body><p>The passwords don't match</p><b><p>Password's don't exist</p>"
                     + "<button class='btn btn-success' "
                     + "onclick=\"location.href = 'http://localhost:8080/SoftSec_Authenticator/home.html';\">Go Back</button>"
                     + "</body></html>");
         }
        
         catch (UndefinedAccountException ex) {
            out.println("<html><head><title>LoginError</title></head><body><p>That account doesn't exist</p><b><p>Username doesn't exist</p>"
                    + "<button class='btn btn-success' "
                    + "onclick=\"location.href = 'http://localhost:8080/SoftSec_Authenticator/home.html';\">Go Back</button>"
                    + "</body></html>");
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