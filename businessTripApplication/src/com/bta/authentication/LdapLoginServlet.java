package com.bta.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.LdapContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bta.ApplicationStartUpListener;

@WebServlet("/loginServlet")
public class LdapLoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(LdapLoginServlet.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOGGER.info("Reached login servlet...");
		
		//testLdapConnectionManually();
		testLdapConnectionWithJndi();
		
		System.out.println("Reached login servlet...");
		String userName = request.getParameter("j_username");  
	    String pwd = request.getParameter("j_password");  
		
	    System.out.println("Username: " + userName);
	    System.out.println("password: " + pwd);
	    
	    //TODO: use credentials to process LDAP authentication
	    
		PrintWriter out = response.getWriter();
		out.print("We good");
		out.close();
	}
	
	public void login() throws NamingException{
		
		//TODO: get paramter username and password
		
		// TODO: prevent LDAP injection in search!!
		
	}
	
	public void testLdapConnectionManually(){
		
		// here using hashtable instead of properties
		LOGGER.info("establishing manual Ldap connection....");
		Hashtable<String,String> env = new Hashtable<String,String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
		//env.put(Context.SECURITY_AUTHENTICATION, "simple");
		// SECURITY_PRINCIPAL: insert LDAP dn, later for authentication
		//env.put(Context.SECURITY_PRINCIPAL, "uid=tester,ou=People,dc=cispa,dc=saarland"); 
		// SECURITY_CREDENTIALS: insert attribute 'userPassword', later for authentication
		//env.put(Context.SECURITY_CREDENTIALS, "{SASL}tester@XXXXXX");
		
		// why DirContext instead of InitialDirContext? dunno...
		//InitialDirContext context = new InitialDirContext(env);
		try {
			DirContext context = new InitialDirContext(env);
			Attributes attrs = context.getAttributes("uid=tester,ou=People,dc=cispa,dc=saarland");
			System.out.println("Surname: " + attrs.get("sn").get());
			System.out.println("Common name : " + attrs.get("cn").get());
			context.close();
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		System.out.println("LDAP connection successful");
		
	}
	
	public void testLdapConnectionWithJndi(){
		
		LOGGER.info("establishing JNDI Ldap connection....");
		// ******** Method 1 : using resource from context.xml *********
		//TODO
		Context ctx = null;
		try {
			ctx = new InitialContext();
			LdapContext ldapContext  = (LdapContext) ctx.lookup("java:comp/env/ldap/ldapResource");
			Attributes attrs = ldapContext.getAttributes("uid=tester,ou=People,dc=cispa,dc=saarland");
			System.out.println("Surname: " + attrs.get("sn").get());
			System.out.println("Common name : " + attrs.get("cn").get());
			ldapContext.search("",null);
			ldapContext.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static SearchControls getSearchControls() {
        SearchControls cons = new SearchControls();
        cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] attrIDs = {"distinguishedName", "cn", "givenname"};
        cons.setReturningAttributes(attrIDs);
        return cons;
    }

}
