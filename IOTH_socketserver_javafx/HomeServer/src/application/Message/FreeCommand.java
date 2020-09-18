package application.Message;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Server.SSLSocketClient;
import application.Server.SocketClient;

public class FreeCommand extends MainCommand {
	public FreeCommand(SocketClient client, Connection con) {
		super(client,con);		
	}
	
	public void run(String msg) {
		if(getMyAccount(msg)) {return;}
		client.onSend("잘 모르겠습니다");
	}
	public boolean getMyAccount(String msg)
	{
		String query="Select * from AccountSet1 where s_name=?";
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, msg);
			ResultSet rs=pstmt.executeQuery();
			
			while(rs!=null&&rs.next())
			{				
				String result =""+msg +"의 계정입니다.\n";
				result+="ID:"+rs.getString("S_USERID")+"\n";
				result+="PW:"+rs.getString("S_USERPW");
				client.onSend(result);
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	

}
