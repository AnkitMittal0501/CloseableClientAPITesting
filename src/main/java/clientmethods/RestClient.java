package clientmethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.api.test.TestBase;
import org.api.utility.IteratorUtility;
import org.json.JSONArray;
import org.json.JSONObject;

public class RestClient {
	HashMap<String, Object> map;
	List<List<HashMap<Object, Object>>> list;
	List<HashMap<Object, Object>> listOfMap;
	HashMap<Object, Object> jsonArray;
	HashMap<Object, Object> jsonObject;
	List<HashMap<Object, Object>> listOfJsonArray;
	public CloseableHttpResponse response;

	public HashMap<String, Object> getMethod(String url) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet get = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(get);
			int statusCode = response.getStatusLine().getStatusCode();

			System.out.println(statusCode);
			String res = EntityUtils.toString(response.getEntity(), "UTF-8");
			System.out.println(res);
			JSONObject json = new JSONObject(res);
			System.out.println(json);
			Header[] headerArray = response.getAllHeaders();
			map = new HashMap<String, Object>();
			list = new ArrayList<List<HashMap<Object, Object>>>();

			jsonObject = new HashMap<Object, Object>();
			listOfJsonArray = new ArrayList<HashMap<Object, Object>>();
			Iterator<String> itr = json.keys();
			while (itr.hasNext()) {
				String key = itr.next();
				System.out.println(key);
				System.out.println(json.get(key));
				Object objValue = json.get(key);
				String str = objValue.toString();
				System.out.println(str);

				if (str.contains("[")) {

					JSONArray array = (JSONArray) objValue;
					System.out.println("This is JsonArray" + array);
					for (int i = 0; i < array.length(); i++) {
						jsonArray = new HashMap<Object, Object>();
						JSONObject resObj = array.getJSONObject(i);
						Iterator<String> newKey = resObj.keys();
						while (newKey.hasNext()) {

							String nk = newKey.next();
							String value = resObj.get(nk).toString();
							System.out.println(value);
							jsonArray.put(nk, value);

						}
						listOfJsonArray.add(jsonArray);
					}

				} else {
					jsonObject.put(key, str);
				}

				map.put("JSONArray", listOfJsonArray);
			}

			/*
			 * Iterator<String> itr = json.keys(); jsonArray = new HashMap<Object,
			 * Object>(); jsonObject = new HashMap<Object, Object>(); while (itr.hasNext())
			 * { String key=IteratorUtility.iteratorValue(json, itr); String str =
			 * IteratorUtility.jsonParser(json, itr); Object objValue=json.get(key);
			 * System.out.println(str);
			 * 
			 * if (str.contains("[")) { //System.out.println(itr.next().toString());
			 * JSONArray array = (JSONArray)objValue; System.out.println("This is JsonArray"
			 * + array); for (int i = 0; i < array.length(); i++) { JSONObject resObj =
			 * array.getJSONObject(i); Iterator<String> newKey = resObj.keys(); while
			 * (newKey.hasNext()) { String nk = newKey.next(); String
			 * value=IteratorUtility.jsonParser(resObj, newKey); jsonArray.put(nk, value);
			 * 
			 * } }
			 * 
			 * } else { jsonObject.put(key, str); }
			 * 
			 * }
			 * 
			 */
			map.put("JSONObject", jsonObject);

			HashMap<String, String> allheaders = new HashMap<String, String>();
			for (Header header : headerArray) {
				allheaders.put(header.getName(), header.getValue());
			}
			System.out.println(allheaders);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(map);
		return map;
	}

	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> map) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new StringEntity(entityString));
			for (Map.Entry<String, String> entry : map.entrySet()) {
				httppost.addHeader(entry.getKey(), entry.getValue());

			}

			response = httpClient.execute(httppost);

		} catch (Exception e) {

		}
		return response;
	}
}
