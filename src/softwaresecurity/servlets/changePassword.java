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
import softwaresecurity.authenticator.Authenticator;
import softwaresecurity.authenticator.AuthenticatorInterface;
/**
 * Created by MigMg on 11/10/2016.
 */


@WebServlet("/changepwd")
public class changePassword extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public changePassword() {
        super();
        // TODO Auto-generated constructor stub
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the MIME type for the response message
        response.setContentType("text/html");
        // Get a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();
        AuthenticatorInterface authenticator = new Authenticator();
        try{
            //login
            authenticator.change_pwd(request.getParameter("username"), request.getParameter("password1"), request.getParameter("password2"));
            //redirect to home page
            response.sendRedirect("http://localhost:8080/SoftSec_Authenticator/home.html");

        } catch (UndefinedAccountException ex) {
            out.println("<html><head><title>LoginError</title></head><body><p>Something went wrong</p><b><p>Password Not Changed</p>"
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
