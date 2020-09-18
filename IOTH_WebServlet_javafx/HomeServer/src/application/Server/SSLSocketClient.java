package application.Server;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.net.ssl.SSLSocket;

import application.MainUnit;
import application.Message.MessageAnalyzer;
import javafx.scene.image.Image;

public class SSLSocketClient {
	/*public SSLSocket socket;
	public String uid;
	public boolean Login =false;
	
	private MessageAnalyzer analyzer;
	
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;

	
	private Thread forReceive=null;
	private boolean LifeforReceive=true;
	
	
	
	public SSLSocketClient(SSLSocket socket)
	{
		this.socket=socket;
		
		uid=UUID.randomUUID().toString();
		//this.analyzer=analyzer;
		analyzer=new MessageAnalyzer(this);
		try {
			
			is=socket.getInputStream();
			
			
			dis=new DataInputStream(is);
	
		
			os=socket.getOutputStream();
			dos=new DataOutputStream(os);


			
		} catch (IOException e) {
			//is.
			e.printStackTrace();
		}
		forReceive=new Thread(new Receive());
		onReceive();
		
		
	}
	public void close() {
		LifeforReceive=false;
		try {				
		analyzer.Close();
		ClientsList.list.remove(this);
		System.out.println("남은 클라:"+ClientsList.list.size());
		dos.close();
		dis.close();
		socket.close();		
		os.close();
		is.close();
		System.out.println("삭제완료");	

			
		} catch (IOException e) {
			System.out.println("오류");
			e.printStackTrace();
		}
	
	}
	
	
	public void onReceive() {
		if(!forReceive.isAlive()) {
			forReceive.start();
		}else
		{
			return;
		}
	}
	public void onSend(String msg) {
		if(Login)
		{
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				System.out.println("dos전송실패");
				e.printStackTrace();
			}
		
		}
		
		
		
	}
	class Receive implements Runnable{		
		public void run() {
		//	new Thread(new Receive_Test()).start();
			
			
			String res=null;
			try {
				while(LifeforReceive&&(res=dis.readUTF())!=null)
				{
					switch(res)
					{
						case "SystemCmd#":
						{
							analyzer.ReceiveSystemMsg(dis.readUTF());
							break;
						}
						case "FunctionCmd#":
						{break;}
						case "NomalMsg#":
						{
							analyzer.ReceiveMsg(dis.readUTF());
							break;
						}
						case "Image#":
						{
							analyzer.ReceiveImage(dis);
							break;
						}
					}
					
				}
			} catch (IOException e) {
				// 연결끊김
				
				e.printStackTrace();
			}
			close();
			System.out.println("정상종료");
			
		
		}
	}*/
	
}
