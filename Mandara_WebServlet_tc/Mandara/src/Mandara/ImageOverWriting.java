package Mandara;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class ImageOverWriting
 */
@WebServlet("/ImageOverWriting")
public class ImageOverWriting extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DataSource datafactory=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageOverWriting() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		run(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	private void run(HttpServletRequest request, HttpServletResponse response)
	{
		String g_uid=request.getParameter("g_uid");
		String toName=request.getParameter("toName");
		String I_type=request.getParameter("I_type");

		try {							
			datafactory=(DataSource)(new InitialContext().lookup("java:/comp/env/jdbc/oracle"));
			Connection con=datafactory.getConnection();
			String query="Select decode(count(*),'0','false','true') from MandaraUserImage1 where g_uid=? and toName=? and I_type=?";		
			PreparedStatement pstmt=con.prepareStatement(query);
			ResultSet rs=null;
			pstmt.setString(1, g_uid);
			pstmt.setString(2, toName);
			pstmt.setString(3, I_type);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				response.getWriter().println((rs.getString(1)));
			}
			rs.close();
			pstmt.close();
			con.close();
		}catch(Exception e) {System.out.println(e);}

	}

}
