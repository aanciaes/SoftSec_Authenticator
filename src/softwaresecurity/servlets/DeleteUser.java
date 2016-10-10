package softwaresecurity.servlets;

import exceptions.AccountLockedException;
import exceptions.AuthenticationError;
import exceptions.UndefinedAccountException;
import softwaresecurity.authenticator.Account;
import softwaresecurity.authenticator.Authenticator;
import softwaresecurity.authenticator.AuthenticatorInterface;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Created by MigMg on 10/10/2016.
 */


/*
        Falta testar

 */
@WebServlet("/deleteuser")
public class DeleteUser extends HTTPServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Set the MIME type for the response message
        response.setContentType("text/html");

        // Get a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();
        AuthenticatorInterface authenticator = new Authenticator();
        HttpSession session = request.getSession(true);

        try{
            //login

            String authUser = session.getAttribute("USER").toString();
            if(!authUser.equals(ROOT)){
                ;
            }
            else{
                int result = authenticator.deleteAccount(request.getParameter("username"));

                //Nao estava locked
                if(result==0){

                }
                else
                {

                }
            }
        } catch (UndefinedAccountException | AccountLockedException | AuthenticationError ex) {
            out.println("<html><head><title>LoginError</title></head><body><p>Login Failed</p>"
                    + "<button class='btn btn-success' "
                    + "onclick=\"location.href = 'http://localhost:8080/SoftSec_Authenticator/login.html';\">Go Back</button>"
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
