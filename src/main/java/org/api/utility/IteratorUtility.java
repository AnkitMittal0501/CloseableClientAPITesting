package org.api.utility;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class IteratorUtility {


	public static String jsonParser(JSONObject json,Iterator<String> itr) {
		
	
			
		
			String str = json.get(iteratorValue(json,itr)).toString();
			System.out.println(str);
			return str;
	
	}
	public static String iteratorValue(JSONObject json,Iterator<String> itr)
	{
		String key=itr.next();
		return key;
	}
}
