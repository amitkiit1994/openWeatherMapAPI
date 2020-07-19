package org.openWeatherMap.test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openWeatherMap.TestUtil.TestUtil;
import org.openWeatherMap.base.TestBase;
import org.openWeatherMap.client.RestClient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit test for simple App.
 */
public class GetAPITest extends TestBase {
	public static Logger log = LogManager.getLogger(GetAPITest.class.getName());

	/**
	 * Rigorous Test :-)
	 * 
	 */
	TestBase testbase;
	String apiUrl;
	String serviceUrl;
	String url;
	String city;
//	String country;
	String appid;
//	String page;
	CloseableHttpResponse httpResponse;
	RestClient restClient;

	@BeforeMethod
	public void setUp() throws URISyntaxException {
		testbase = new TestBase();
		appid=prop.getProperty("API_KEY");
		city=prop.getProperty("City");
//		country=prop.getProperty("Country");
		apiUrl = prop.getProperty("URL");
//		page = prop.getProperty("Page");
		serviceUrl = prop.getProperty("serviceURL");
		url = apiUrl + serviceUrl;
		System.out.println(url);
	}

	@Test
	public void getTest() throws ParseException, IOException, URISyntaxException {
		restClient = new RestClient();
		URIBuilder builder = new URIBuilder(url);
		builder.setParameter("q", city).setParameter("appid", appid);
	//	builder.setParameter("page", page);
		System.out.println(builder.build());
		httpResponse = restClient.get(builder.build());

		// getStatus
		int statusCode=Integer.valueOf(200);
		if (TestBase.verifyStatus(httpResponse,statusCode )) {
			log.info("Status code verified");
			System.out.println("Status code verified: "+statusCode);
		} else {
			fail();
			log.info("Status code verification failed");
			System.out.println("Status code verified");
		}
		// getResponse
		
		
		
		JSONObject jsonRepsonse=TestBase.getResponseString(httpResponse);
		System.out.println("Json response: "+jsonRepsonse);
		String filePath=System.getProperty("user.home")+"\\weatherReportAPI.txt";
		TestUtil.createFile(filePath);
		TestBase.writeWeatherReportToFile(filePath, jsonRepsonse);
//		System.out.println("City: "+TestUtil.getValueByJPath(jsonRepsonse, "/name"));
//		System.out.println("Weather: "+TestUtil.getValueByJPath(jsonRepsonse, "/weather[0]/main"));
//		System.out.println("Wind: "+TestUtil.getValueByJPath(jsonRepsonse, "/wind/speed"));
//		System.out.println("Humidity: "+TestUtil.getValueByJPath(jsonRepsonse, "/main/humidity"));
//		System.out.println("Temp in Degrees: "+TestUtil.kelvinToDegree(TestUtil.getValueByJPath(jsonRepsonse, "/main/temp")));
//		System.out.println("Temp in Farheneit: "+TestUtil.kelvinToFarheneit(TestUtil.getValueByJPath(jsonRepsonse, "/main/temp")));

		
		
		// headers
		System.out.println("Headers: "+TestBase.getHeaderHashMap(httpResponse));

	}
}
