package Mandara;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class UserAdder
 */
@WebServlet("/UserAdder")
public class UserAdder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAdder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		useradd(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void useradd(HttpServletRequest request, HttpServletResponse response)
	{
		String g_uid=request.getParameter("g_uid");
		
		if(g_uid!=null)
		{
		
			DataSource datafactory=null;
			try {				
			datafactory=(DataSource)(new InitialContext().lookup("java:/comp/env/jdbc/oracle"));
			}catch(Exception e) {}
			
			Connection con=null;
			PreparedStatement pstmt=null;
			String query="Insert into MandaraUser1 values('"+g_uid+"')";
			try {
				con=datafactory.getConnection();
				pstmt=con.prepareStatement(query);
				pstmt.executeQuery();
				
			}catch(Exception e) {System.out.println(e);}
			try{
				pstmt.close();				
				con.close();
			}catch(Exception e)
			{
				
			}
			try{response.getWriter().println("True");}catch(Exception e){}
		}//if(g_uid!=null)
		else
		{
			try{response.getWriter().println("false");}catch(Exception e){}
		}
		
				
	}

}
