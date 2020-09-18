package MandaraOffer;

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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class MandaraListOffer
 */
@WebServlet("/MandaraListOffer")
public class MandaraListOffer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DataSource datafactory=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MandaraListOffer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ImageOffer(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void ImageOffer(HttpServletRequest request,HttpServletResponse response)
	{
		try {
			datafactory=(DataSource)(new InitialContext().lookup("java:/comp/env/jdbc/oracle"));
			Connection con =datafactory.getConnection();
			String query="Select No, Mandaraname from MandaraImageOffer1";
			ResultSet rs=null;
			JSONObject jsonObject=new JSONObject();
			JSONArray jsonArray=new JSONArray();
			
			
			PreparedStatement pstmt=con.prepareStatement(query);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				JSONObject obj=new JSONObject();
				obj.put("Num", rs.getInt(1));
				obj.put("Name", rs.getString(2));
				jsonArray.add(obj);
			}
			jsonObject.put("MandaraData",jsonArray);
			response.getWriter().println(jsonObject.toJSONString());
			rs.close();
			pstmt.close();
			con.close();
		}catch(Exception e)
		{
			System.out.println("만다라제공이미지 리스트 출력에러 : "+e);
		}
	}

}
