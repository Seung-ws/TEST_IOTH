package application.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Server.SSLSocketClient;
import application.Server.SocketClient;

public class SystemCommand extends MainCommand{
	
	public SystemCommand(SocketClient client, Connection con) {
		super(client, con);
		// TODO Auto-generated constructor stub
	}




	public void run(String msg) {
		if(msg.contains("New-LoginSignedAccepter")) {getLogin(msg.replace("New-LoginSignedAccepter::", ""));return;}		
		client.onSend("시스템명령을 찾을수 없습니다.");
	}
	
	
	
	
	public void getLogin(String msg) {
		String [] user=msg.split("::");		
		String Query= "select decode(count(*),1,'true','false')as result from AcceptUser where userid=? and userpw=?";
		try {
		pstmt=con.prepareStatement(Query);
		user[0]=user[0].replace("USERID=","");
		user[1]=user[1].replace("USERPW=","");
		pstmt.setString(1, user[0]);
		pstmt.setString(2, user[1]);
		ResultSet rs=pstmt.executeQuery();
		while(rs!=null&&rs.next())
		{
			System.out.println("로그인결과:"+rs.getString("result"));
			if(client.Accept=Boolean.parseBoolean(rs.getString("result")))
			{
				client.onSend("어서오세요 "+user[0]+"님");
			}
			
		}
		
		} catch (SQLException e) {
			System.out.println("pstmt입력 오류");
			e.printStackTrace();
		}

	}
	
}
