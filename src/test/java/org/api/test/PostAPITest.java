package org.api.test;

import java.io.File;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.databind.ObjectMapper;

import clientmethods.RestClient;
import user.qa.data.Users;

public class PostAPITest extends TestBase {
	TestBase base;
	String url;
	String api;
	String actualUrl;
	RestClient rest;

	@BeforeMethod
	public void testsetup() {
		base = new TestBase();
		url = prp.getProperty("URL");
		api = prp.getProperty("api");
		actualUrl = url + api;
		System.out.println(actualUrl);

	}

	@Test
	public void postAPITest() {
		try {
			rest = new RestClient();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Content-Type", "application/json");
			// jackson api
			ObjectMapper mapper = new ObjectMapper();
			Users user = new Users("Ankit", "lead");
			// object to json file
			mapper.writeValue(new File(
					"C:\\Users\\My Pc\\eclipse-workspace_photon\\api.zip_expanded\\src\\main\\java\\com\\qa\\config\\data,json"),
					user);
			// object to json string
			String userJson = mapper.writeValueAsString(user);
			System.out.println(userJson);
			// calling methodto execute Post api
			CloseableHttpResponse response = rest.post(actualUrl, userJson, map);

			// validate status code
			int statusCode = response.getStatusLine().getStatusCode();
			Assert.assertEquals(statusCode, STATUS_CODE_201);

			// create  Json Object from Json String 
			String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
			JSONObject json = new JSONObject(responseString);
			System.out.println(json);

			// validate value
			//json to java object
			Users userResponse = mapper.readValue(responseString, Users.class);
			System.out.println(userResponse);
			
			// Validate json
			
			Assert.assertTrue(user.getName().equals(userResponse.getName()));
			Assert.assertTrue(user.getJob().equals(userResponse.getJob()));
			System.out.println();
		} catch (Exception e) {

		}

	}
}
