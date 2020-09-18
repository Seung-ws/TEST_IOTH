package application.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

import application.DataBase.DBConnector;
import application.Server.SSLSocketClient;
import application.Server.SocketClient;

public class MessageAnalyzer extends DBConnector{

	
	
	
	private SystemCommand sc=null;
	
	private FreeCommand cmd=null;
	private FileCommand fc=null;
	private SocketClient client;
	
	
	
	
	public MessageAnalyzer(SocketClient client)
	{
		this.client=client;
		
		if(tryConnect()) {	
			
			System.out.println("DB연결성공");
			
			sc=new SystemCommand(this.client,con);
			fc=new FileCommand(this.client,con);
			cmd=new FreeCommand(this.client,con);
		}else
		{
			System.out.println("DB연결실패");
		}
	}

	public void ReceiveSystemMsg(String msg)
	{
		sc.run(msg);
	}
	public void ReceiveMsg(String msg)
	{
		cmd.run(msg);
	}
	public void ReceiveImage(DataInputStream dis)
	{
		fc.SetImageFile(dis);		
	}
	public void Close()
	{
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
