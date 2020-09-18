package Mandara;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class UserImageAdder
 */
@WebServlet("/UserImageAdder")
public class UserImageAdder extends HttpServlet {
	DataSource datafactory=null;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserImageAdder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		userimageadder(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void userimageadder(HttpServletRequest request, HttpServletResponse response)
	{
		int size=100*1024*1024;
		String dir=getServletContext().getRealPath("upload");
	
		String g_uid=request.getParameter("g_uid");
		String toName=request.getParameter("toName");
		String I_type=request.getParameter("I_type");
		
		

		try {				
			datafactory=(DataSource)(new InitialContext().lookup("java:/comp/env/jdbc/oracle"));
			}catch(Exception e) {}
		
		
		if(g_uid!=null&&toName!=null&&I_type!=null)
		{
			dir+="\\"+g_uid;
			File f=new File(dir);
			if(!f.mkdir())
			{
				f.mkdir();
			}
			
			RecordAdder(g_uid,toName,I_type);
			
				
			try {
				MultipartRequest mr=new MultipartRequest(request,dir,size,"UTF-8");
				imageAdder(g_uid,toName,I_type);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
				
		}	
				
		
	}
	private void imageAdder(String g_uid,String toName,String I_type) {
		String path=getServletContext().getRealPath("upload")+"\\"+g_uid;
		
		File f=new File(path);
		byte[] ii=null;
		byte[] oi=null;
		byte[] mi=null;
		byte[] maini=null;
		if(!f.mkdir())
		{
			

			File iconImage=new File(path+"\\iconImage.jpeg");	
			
			if(iconImage.exists())
				try {
					ii=Files.readAllBytes(iconImage.toPath());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			File originImage=new File(path+"\\originImage.png");
			if(originImage.exists())
				try {
					oi=Files.readAllBytes(originImage.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			File mirrorImage=new File(path+"\\mirrorImage.png");
			if(mirrorImage.exists())
				try {
					mi=Files.readAllBytes(mirrorImage.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			File mainImage=new File(path+"\\mainImage.png");
			if(mainImage.exists())
				try {
					maini=Files.readAllBytes(mainImage.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
		Connection con=null;
		PreparedStatement pstmt=null;
	
		String query="Update MandaraUserImage1 set iconImage=?,originImage=?,mirrorImage=?,mainImage=? where g_uid=? AND toName=? AND I_type=?";
		try {
			con=datafactory.getConnection();
			pstmt=con.prepareStatement(query);
			pstmt.setBytes(1, ii);
			pstmt.setBytes(2, oi);
			pstmt.setBytes(3, mi);
			pstmt.setBytes(4, maini);
			pstmt.setString(5, g_uid);
			pstmt.setString(6, toName);
			pstmt.setString(7, I_type);
			pstmt.executeQuery();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
				
	}
	
	
	
	private void RecordAdder(String g_uid,String toName,String I_type) {
		boolean nullCheck=false;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String query="Select Decode(count(*),'0','true','false') from MANDARAUSERIMAGE1 where g_uid=? AND toName=? AND I_type=?";
		try {
			con=datafactory.getConnection();
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, g_uid);
			pstmt.setString(2, toName);
			pstmt.setString(3, I_type);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				
				nullCheck=Boolean.parseBoolean(rs.getString(1));
			
			}
		
			if(nullCheck)
			{
				query="Insert Into MandaraUserImage1 values(?,?,?,?,?,?,?,?)";
				pstmt=con.prepareStatement(query);
				pstmt.setString(1, g_uid);
				pstmt.setString(2, toName);
				pstmt.setString(3, I_type);		
				pstmt.setBytes(4, null);
				pstmt.setBytes(5, null);
				pstmt.setBytes(6, null);
				pstmt.setDate(7, new Date(System.currentTimeMillis()));		
				pstmt.setBytes(8, null);
				pstmt.executeQuery();
			}
			pstmt.close();
			rs.close();
			con.close();
		}catch(Exception e) {
			System.out.println(e);
		}
			
		
	}
	
	
	

}
