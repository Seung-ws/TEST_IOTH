package MandaraOffer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class MandaraTargetImageOffer
 */
@WebServlet("/MandaraTargetImageOffer")
public class MandaraTargetImageOffer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DataSource datafactory=null; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MandaraTargetImageOffer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		getMandaraImage(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void getMandaraImage(HttpServletRequest request, HttpServletResponse response)
	{
		String Number=request.getParameter("Num");
		String m_name=request.getParameter("MandaraName");
		if(Number!=null&&m_name!=null)
		{
			String query="Select MandaraFile from MandaraImageOffer1 where No=? And MandaraName=?";
			try {
				datafactory=(DataSource)(new InitialContext().lookup("java:/comp/env/jdbc/oracle"));
				Connection con=datafactory.getConnection();
				PreparedStatement pstmt=con.prepareStatement(query);
				ResultSet rs=null;
				Blob blob =null;
				pstmt.setInt(1, Integer.parseInt(Number));
				pstmt.setString(2, m_name);
				rs=pstmt.executeQuery();
				while(rs.next())
				{
					blob=rs.getBlob(1);
				}
				if(blob!=null)
				{
				    response.setContentType("image/jpg");
				    response.setHeader("Content-Disposition", "attachment;filename="+m_name+".png");
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
			}catch(Exception e)
			{
				System.out.println("만다라 이미지 제공 비트맵 오류:"+e);
			}
		}
	}

}
