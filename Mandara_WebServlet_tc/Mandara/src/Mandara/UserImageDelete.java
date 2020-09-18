package Mandara;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class UserImageDelete
 */
@WebServlet("/UserImageDelete")
public class UserImageDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DataSource datafactory=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserImageDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ImageDelete(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void ImageDelete(HttpServletRequest request,HttpServletResponse response)
	{
		
		String g_uid=request.getParameter("g_uid");
		String toName=request.getParameter("toName");
		String I_type=request.getParameter("I_type");
		if(g_uid!=null&&toName!=null&&I_type!=null)
		{
			try {
				datafactory=(DataSource)(new InitialContext().lookup("java:/comp/env/jdbc/oracle"));
				Connection conn=datafactory.getConnection();
				String query="Delete from MandaraUserImage1 where g_uid=? AND toName=? AND I_type=?";
				PreparedStatement pstmt=conn.prepareStatement(query);
				pstmt.setString(1, g_uid);
				pstmt.setString(2, toName);
				pstmt.setString(3, I_type);
				pstmt.executeQuery();
				pstmt.close();
				conn.close();
				
				
			}catch(Exception e)
			{
				System.out.println("유저 저장 이미지 삭제 :오류"+e);
			}	
		}
		
	}

}
