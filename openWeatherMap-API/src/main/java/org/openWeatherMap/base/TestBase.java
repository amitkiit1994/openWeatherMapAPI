package org.openWeatherMap.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;

import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.openWeatherMap.TestUtil.TestUtil;

/**
 * Hello world!
 *
 */
public class TestBase 

{
	public static Properties prop;
	public TestBase() {
		// TODO Auto-generated constructor stub
		try {
		prop = new Properties();
		String propertiesFilePath = System.getProperty("user.dir")+"\\src\\main\\java\\org\\openWeatherMap\\config\\config.properties";
		FileInputStream fis=new FileInputStream(propertiesFilePath);;
		prop.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	

	
	public static boolean verifyStatus(CloseableHttpResponse httpResponse, int statusCode) {
		
		int actualStatus=httpResponse.getStatusLine().getStatusCode();
		if(actualStatus==statusCode) {
			return true;
		}
		return false;
		
	}
	public static JSONObject getResponseString(CloseableHttpResponse httpResponse) {
		String responseString="";
		JSONObject responseJson=null;
		try {
			responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			responseJson = new JSONObject(responseString);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseJson;
		
	}
	
	public static void writeWeatherReportToFile(String filePath, JSONObject jsonRepsonse) throws IOException {
//		JSONObject jsonRepsonse=TestBase.getResponseString(httpResponse);
		FileWriter myWriter = new FileWriter(filePath); 
//		System.out.println("Json response: "+jsonRepsonse);
		System.out.println("City: "+TestUtil.getValueByJPath(jsonRepsonse, "/name"));
		myWriter.write("City: "+TestUtil.getValueByJPath(jsonRepsonse, "/name")+"\r\n");
		System.out.println("Weather: "+TestUtil.getValueByJPath(jsonRepsonse, "/weather[0]/main"));
		myWriter.write("Weather: "+TestUtil.getValueByJPath(jsonRepsonse, "/weather[0]/main")+"\r\n");
		System.out.println("Wind: "+TestUtil.getValueByJPath(jsonRepsonse, "/wind/speed"));
		myWriter.write("Wind: "+TestUtil.getValueByJPath(jsonRepsonse, "/wind/speed")+"\r\n");
		System.out.println("Humidity: "+TestUtil.getValueByJPath(jsonRepsonse, "/main/humidity"));
		myWriter.write("Humidity: "+TestUtil.getValueByJPath(jsonRepsonse, "/main/humidity")+"\r\n");
		System.out.println("Temp in Degrees: "+TestUtil.kelvinToDegree(TestUtil.getValueByJPath(jsonRepsonse, "/main/temp")));
		myWriter.write("Temp in Degrees: "+TestUtil.kelvinToDegree(TestUtil.getValueByJPath(jsonRepsonse, "/main/temp"))+"\r\n");
		System.out.println("Temp in Farheneit: "+TestUtil.kelvinToFarheneit(TestUtil.getValueByJPath(jsonRepsonse, "/main/temp")));
		myWriter.write("Temp in Farheneit: "+TestUtil.kelvinToFarheneit(TestUtil.getValueByJPath(jsonRepsonse, "/main/temp"))+"\r\n");
		myWriter.close();
	}
	
	public static HashMap<String, String> getHeaderHashMap(CloseableHttpResponse httpResponse){
		Header[] headerArray = httpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		return allHeaders;
	}
  
}
