package getMandaraImage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class IconImageGetter
 */
@WebServlet("/MainImageGetter")
public class MainImageGetter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DataSource datafactory=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainImageGetter() {
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
			String toName=request.getParameter("toName");
			String I_type=request.getParameter("I_type");
			
			if(g_uid!=null&&toName!=null&&I_type!=null)
			{
				datafactory=(DataSource)(new InitialContext().lookup("java:/comp/env/jdbc/oracle"));
				Connection con=datafactory.getConnection();
				String query = "Select MainImage from MandaraUserImage1 where g_uid=? AND toName=? AND I_type=?";				
				PreparedStatement pstmt=con.prepareStatement(query);
				ResultSet rs=null;
				Blob blob=null;
				pstmt.setString(1, g_uid);
				pstmt.setString(2, toName);
				pstmt.setString(3, I_type);
				rs=pstmt.executeQuery();
				while(rs.next())
				{
					blob=rs.getBlob(1);
				}
				if(blob!=null)
				{
				    response.setContentType("image/jpg");
				    response.setHeader("Content-Disposition", "attachment;filename="+toName+".png");
					InputStream is=blob.getBinaryStream();
					ServletOutputStream sos=response.getOutputStream();
					byte[] b=new byte[4096];
					for(int len=is.read(b);len!=-1;len=is.read(b))
					{
						sos.write(b, 0, len);
					}
					sos.flush();
					sos.close();
					is.close();
				}
				rs.close();
				pstmt.close();
				con.close();
			}
			
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
}
