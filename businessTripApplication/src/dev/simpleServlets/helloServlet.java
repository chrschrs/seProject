package dev.simpleServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class helloServlet
 */
@WebServlet("/helloServlet")
public class helloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public helloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String dbUrl = "java:comp/env/jdbc/testdb";
		//String dbUrl = "jdbc/testdb";
		Context ctx;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(dbUrl);
			Connection connection = ds.getConnection();
			System.out.println("DB connection succesful");
			connection.close();
			System.out.println("Closing connection succesful");
		} catch (NamingException | SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("FAIL: DB connection error");
			e.printStackTrace();
		}
        
		response.getWriter().print("What's hatnin?");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
