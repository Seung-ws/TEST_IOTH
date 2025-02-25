package application.Server;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.net.ssl.SSLSocket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import application.MainUnit;
import application.DataBase.DBImageLog;
import application.Message.MessageAnalyzer;
import application.Message.homeServerChat;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class SocketClient {
	public Socket socket;
	public String uid=null;
	public String client_type=null;
	public String client_name=null;
	public String client_usage=null;
	public boolean Accept =false;
	
//	private MessageAnalyzer analyzer;
	private homeServerChat hsc=null;
	
	private InputStream is;
	private OutputStream os;
	//private DataInputStream dis;
//	private DataOutputStream dos;

	
	private Thread forReceive=null;
	private boolean LifeforReceive=true;
	
	
	
	public SocketClient(Socket socket)
	{
		this.socket=socket;
			
		try {
			socket.setSoTimeout(0);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//this.analyzer=analyzer;
	//	analyzer=new MessageAnalyzer(this);
		hsc=new homeServerChat();
		try {
			
			is=socket.getInputStream();
			
			
			//dis=new DataInputStream(is);
	
		
			os=socket.getOutputStream();
		//	dos=new DataOutputStream(os);


			
		} catch (IOException e) {
			//is.
			e.printStackTrace();
		}
		forReceive=new Thread(new Receive());
		onReceive();
		
		
	}
	public void close() {
		
		try {				
			socket.close();		
			os.close();
			is.close();
			LifeforReceive=false;
		//analyzer.Close();
		ClientsList.list.remove(this);

		MainUnit.MainMsg("클라이언트 종료:"+uid+"\n"
								+"클라이언트 이름:"+client_name+"\n"
								+"클라이언트 타입:"+client_type+"\n");
		//MainUnit.MainMsg("클라이언트 현황:"+ClientsList.list.size());
		//dos.close();
		//dis.close();
		client_type=null;
		client_usage=null;
		client_name=null;
	

			
		} catch (IOException e) {
			MainUnit.MainMsg("종료오류:"+e);

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
	public boolean onSend(String msg) {
		if(client_name!=null&&client_name.equals("WebCam"))
		{
			DataOutputStream dos=new DataOutputStream (os);
			try{
			dos.writeUTF(msg);
			dos.flush();
			}catch(Exception e)
			{
				System.out.println("toWebCam : dos write err"+e);
			}
			return true;
		}else {
			try {	
				os.write(msg.getBytes("UTF-8"));
				
				os.flush();
				return true;
			} catch (Exception e) {
				System.out.println("Send Error 에러"+e);
		
				
			} 
		}
		
		return false;
		
		
	}
	class Receive implements Runnable{		
		public void run() {
			String AcceptRes=null;
			String tmp=null;
			
			
		
			while(LifeforReceive)
			{
				try{
				if(!Accept)
				{
					
					byte[] b=new byte[100];
					int len= is.read(b);
					if(len==0)
					{
						throw new Exception();
					}
					onSend("ROK");
					AcceptRes=new String(b,0,len,"UTF-8");
					System.out.println(AcceptRes);
					if(AcceptRes!=null)
					{
						JSONObject totalObject=(JSONObject) new JSONParser().parse(AcceptRes);
						JSONArray jsonArr=(JSONArray)totalObject.get("#!sys");
						JSONObject object=(JSONObject)jsonArr.get(0);
						client_usage=object.get("client_usage").toString();
						client_type=object.get("client_type").toString();
						client_name=object.get("client_name").toString();
						MainUnit.MainMsg("클라이언트 등록:"+uid+"\n"
								+"타입:"+client_type+"\n"
								+"용도:"+client_usage+"\n"
								+"네임:"+client_name+"\n");
						
						Accept=true;
						AcceptRes=null;
					}
					AcceptRes=null;
					
				}else if(client_name.contentEquals("WebCam")){
				////////////
					imageReceiver(is);
				}
				else// if (client_name.equals("user"))
				{
					
					byte[] b=new byte[4096];
					int len= is.read(b);
					System.out.println("len="+len);
					if(len==0)
					{
						
						throw new Exception();
						
					}
					tmp=new String(b,0,len,"UTF-8");
					System.out.println("tmp="+tmp);
					if(tmp!=null)
					{
					
							tmp=hsc.MsgType(tmp);
							if(tmp!=null)onSend(tmp);
							//hsc.MsgType(tmp);
							tmp=null;
						
					}
					tmp=null;
									
					
				}
				}catch(Exception e)
				{
					close();
					
					System.out.println("Receive Error 에러"+e);
					System.out.println(e.fillInStackTrace());
					System.out.println(e.getMessage());
					//close();
				}
				
			}
			
		
			
		//	new Thread(new Receive_Test()).start();
			
			
			/*
			String res=null;
			try {
				while(LifeforReceive&&(res=dis.readUTF())!=null)
				{
					System.out.println(res);
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
			
		*/
		}
	}
	private void imageReceiver(InputStream is)
	{
		System.out.println("WebCamMod 진입");
		
		DataInputStream dis=new DataInputStream(is);
		BufferedInputStream bis=new BufferedInputStream(dis);
		ByteArrayOutputStream baos=new ByteArrayOutputStream(); 
		
		int len=-1;
		int sum=0;

		byte[] data =new byte[8192];
		try {
			String disUTF=dis.readUTF();
			System.out.println(disUTF);
			JSONParser parser =new JSONParser();
			JSONObject object=(JSONObject) parser.parse(disUTF);
			JSONArray arr=(JSONArray) object.get("#!sysWebCam");
			JSONObject o=(JSONObject) arr.get(0);
			int maxSum=Integer.parseInt(o.get("Size").toString());
			//FileOutputStream fos=new FileOutputStream("D:\\a.png");
			
			while(!(sum==maxSum)&&(len=bis.read(data))!=-1)
			{
				sum+=len;
				baos.write(data,0,len);
				//baos.write(data,0,len);					
			}
			ByteArrayInputStream bais=new ByteArrayInputStream(baos.toByteArray());
		//	Image bi=new Image(new ByteArrayInputStream(baos.toByteArray()));
			
			
			
			//MainUnit.Mainbg.setImage(bi);
			
			
			new DBImageLog().setImage(bais);
			System.out.println("db등록 완료");
			
		
		
			
			
		}catch(Exception e)
		{
			close();
			System.out.println("파일쓰기 입출력실패"+e);
		}
	}
	
	
}
