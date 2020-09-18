package application.DataBase;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DBImageLog extends DBConnector {
	public String getImageLog()
	{
		if(tryConnect())
		{
			String query="select name from userimage1 order by name desc";
			try {
				pstmt=con.prepareStatement(query);
				ResultSet rs=pstmt.executeQuery();
				JSONObject total=new JSONObject();
				JSONArray array=new JSONArray();
				while(rs.next())
				{
					JSONObject object=new JSONObject();
					object.put("name", rs.getString(1));
					array.add(object);
				}
				total.put("#tab32f",array);
				return total.toJSONString();
				
				
			}catch(Exception e)
			{
				System.out.println("홈 웹캠 이름 불러오기 에러"+e);
				return null;
			}
		}
		return null;
	}
	
	public void setImage(ByteArrayInputStream bais)
	{
		if(tryConnect())
		{
		
			String query="Insert into UserImage1 values(?,?,?,?,?)";
			SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd_HHmmss");
			long time=System.currentTimeMillis();
			
			try {
				pstmt=con.prepareStatement(query);
				pstmt.setString(1, sdf.format(new Date(time)));
				pstmt.setBinaryStream(2, bais);
				pstmt.setString(3, "jpg");
				pstmt.setDate(4, new Date(time));
				pstmt.setString(5, null);
				pstmt.executeQuery();
				pstmt.close();
				con.close();
			}catch(Exception e)
			{
				
			}
			
			
		}
		
	}

}
