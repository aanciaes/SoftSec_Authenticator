package softwaresecurity.authenticator;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;

import exceptions.AccountLockedException;
import exceptions.AuthenticationError;
import exceptions.UndefinedAccountException;
import softwaresecurity.encryptionAlgorithm.AESencrp;

public class MyRealm extends RealmBase{

	private String username;

	private String password;

	@Override
	public Principal authenticate(String username, String credentials) {

		this.username = username;
		this.password = credentials;

		//System.out.println("Entering tomcatRealmTest1.authenticate");
		AuthenticatorInterface authenticator = new Authenticator();
		try{
			String pwd = AESencrp.encrypt(credentials);
			//login
			authenticator.login(username, pwd);
			return getPrincipal(username);
		}catch (UndefinedAccountException | AccountLockedException | AuthenticationError ex) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Principal getPrincipal(String username) {
		//System.out.println("Entering tomcatRealmTest1.getPrincipal");
		List<String> roles = new ArrayList<String>();
		AuthenticatorInterface authenticator = new Authenticator();
		try{
			String role = authenticator.getRole(username);
			roles.add(role);
			return new GenericPrincipal(username, password, roles);
		}catch (UndefinedAccountException e){
			return null;
		}
	}

	@Override
	protected String getPassword(String string) {
		//System.out.println("Entering tomcatRealmTest1.getPassword");
		return password;
	}

	@Override
	protected String getName() {
		//System.out.println("Entering tomcatRealmTest1.getName");
		return username;
	}

}
