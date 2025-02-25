package application.DataBase;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Stack;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TempHumGasData {
	public static DBTHG dbthg=new DBTHG();
	public static long time=System.currentTimeMillis();
	public static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH");
	public static String tempValue="20";
	public static String gasValue="20";
	public static String humValue="20";
	public static Stack<JSONObject> stack=new Stack<JSONObject>();
	public synchronized static String test_gas_module() {
		try {
		JSONObject total=new JSONObject();
		JSONArray jsonArr=new JSONArray();
		JSONObject object =new JSONObject();
		object.put("Temp","123");
		object.put("Hum", "234");
		object.put("Gas","456");
		jsonArr.add(object);
		total.put("#!sys:AddTGD", jsonArr);

		return total.toJSONString();
		}catch(Exception e)
		{
			System.out.println("임시 온가스추가 "+e);
		}
		return null;
	}
	
	public synchronized static void referesh_temp_hue(String r)
	{
		
		
		try{
			JSONParser p=new JSONParser();
			JSONObject total=(JSONObject) p.parse(r);
			JSONArray jsonArr=(JSONArray)total.get("#!saveTHG");			
			JSONObject object=(JSONObject) jsonArr.get(0);
		
			System.out.println("DB에넣는 THG:"+object.toJSONString());
			dbthg.temp_hue_dbadder(new Date(System.currentTimeMillis()),
					object.get("Temp").toString(),
					object.get("Hum").toString(),
					object.get("Gas").toString());
	
						
		}catch(Exception e)
		{
			
		}

		
	}
	public synchronized static String temp_and_hue_type_m() {
		String res=null;
		try {
			JSONObject totalObject=new JSONObject();
			
			totalObject.put("#tab2m", dbthg.getonedata());
			res=totalObject.toJSONString();
		}catch(Exception e) {
			System.out.println("온습도 m내보내기 에러");
		}
		
		return res;
	}
	public synchronized static String temp_and_hue_type_F()
	{
		try{
			JSONObject t=new JSONObject();
			t.put("#tab2f", dbthg.time7list());
			return t.toJSONString();
		}catch(Exception e)
		{
			System.out.println("온습도 f내보내기 에러");
		}

		return null;
	}

}

