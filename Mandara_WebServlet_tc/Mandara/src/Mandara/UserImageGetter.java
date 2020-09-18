package Mandara;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class IconImageGetter
 */
@WebServlet("/UserImageGetter")
public class UserImageGetter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DataSource datafactory=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserImageGetter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ImageGetter(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void ImageGetter(HttpServletRequest request, HttpServletResponse response) {
		try {
			String g_uid=request.getParameter("g_uid");
			String I_type=request.getParameter("I_type");
		
			JSONArray jsonarr=new JSONArray();
			JSONObject jsonobj=new JSONObject();
			if(g_uid!=null&&I_type!=null)
			{
				datafactory=(DataSource)(new InitialContext().lookup("java:/comp/env/jdbc/oracle"));
				Connection con=datafactory.getConnection();
				String query = "Select toName from MandaraUserImage1 where g_uid=? AND I_type=?";
				ResultSet rs=null;
				PreparedStatement pstmt=con.prepareStatement(query);
				pstmt.setString(1, g_uid);
				pstmt.setString(2, I_type);
				rs=pstmt.executeQuery();
				while(rs.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("toName", rs.getString(1));	
					jsonarr.add(obj);
				}
				jsonobj.put("UserData",jsonarr);
				response.getWriter().println(jsonobj.toJSONString());
				rs.close();
				pstmt.close();
				con.close();
			}
			
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
}
