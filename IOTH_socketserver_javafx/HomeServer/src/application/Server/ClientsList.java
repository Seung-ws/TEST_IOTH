package application.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import application.MainUnit;

public class ClientsList {
	//public static ArrayList<SSLSocketClient> list=new ArrayList<SSLSocketClient>();;

	public static ArrayList<SocketClient> list=new ArrayList<SocketClient>();;
	public static void currentClient() {
		MainUnit.MainMsg.setText(MainUnit.MainMsg.getText().concat("현재인원:"+list.size()));
	}
	public synchronized static  String isAliveClient() {
		Stack<Integer> res=new Stack<Integer>();
		for(int i=0; i<list.size();i++)
		{
			if(!(list.get(i).onSend("Alive")))res.push(i);
		}
		while(!res.isEmpty())
		{
			list.get(res.pop()).close();;
		}
		
		return ""+list.size(); 
	}
	public  synchronized static String getList() {
		isAliveClient();
		JSONArray jsonArr=new JSONArray();
		for(int i=0;i<list.size();i++)
		{
			
			if(!list.get(i).client_type.contentEquals("mobile"))
			{
				JSONObject object=new JSONObject();
				object.put("client_usage",list.get(i).client_usage);
				object.put("client_name",list.get(i).client_name);
				object.put("client_type",list.get(i).client_type);
				jsonArr.add(object);	
			}
			
		}
		JSONObject object=new JSONObject();
		object.put("#tab3f", jsonArr);
		return object.toJSONString();
	}
	public  synchronized static String getRelayList() {
		isAliveClient();
		JSONArray jsonArr=new JSONArray();
		for(int i=0;i<list.size();i++)
		{
			
			if(list.get(i).client_usage.contentEquals("Relay"))
			{
				JSONObject object=new JSONObject();
				object.put("client_usage",list.get(i).client_usage);
				object.put("client_name",list.get(i).client_name);
				object.put("client_type",list.get(i).client_type);
				jsonArr.add(object);	
			}
			
		}
		JSONObject object=new JSONObject();
		object.put("#tab35f", jsonArr);
		return object.toJSONString();
	}
	public synchronized static String deviceCount()
	{
		isAliveClient();
		int len=0;

		for(SocketClient c:list)
		{
			if(!c.client_type.equals("mobile"))
			{
				len+=1;
			}
		}
		JSONObject object=new JSONObject();		
		object.put("deviceCount", ""+len);
		JSONArray jsonArray=new JSONArray();
		JSONObject totalObject=new JSONObject();
		jsonArray.add(object);
		totalObject.put("#tab3m",jsonArray);
		return totalObject.toJSONString();
	}
	
	public synchronized static void toSend(String name,String sign)
	{
		//isAliveClient();
		for(SocketClient c:list)
		{
			if(c.client_name.equals(name))
			{
				c.onSend(sign);
			}
		}
	}
	public synchronized static void AllClose()	
	{
		while(list.isEmpty())
		{
			list.get(0).close();
		}
			
	}
		
	
	
}
