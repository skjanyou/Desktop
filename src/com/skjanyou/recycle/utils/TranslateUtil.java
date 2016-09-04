package com.skjanyou.recycle.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("deprecation")
public class TranslateUtil {
	private static HttpClient httpClient = new DefaultHttpClient();
	public static JSONObject getJsonObject(String from,String to,String query,String transtype,String simple_means_flag) throws Exception{
		JSONObject json = null;
		String uri = "http://fanyi.baidu.com/v2transapi";
		
		String para1 = "from=" + from;
		String para2 = "to=" + to;
		String para3 = "query=" + query;
		String para4 = "transtype=" + transtype;
		String para5 = "simple_means_flag=" + simple_means_flag;
		
		
		String url = uri + "?" + para1 + "&" + para2 + "&" + para3 + "&"  + para4 + "&"  + para5 ;
		System.out.println(url);
		HttpGet get = new HttpGet(url);
		HttpResponse response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
		int statusCode=response.getStatusLine().getStatusCode();
		if (statusCode==HttpStatus.SC_OK) {
			//得到httpResponse的实体数据
			HttpEntity httpEntity=response.getEntity();
			if (httpEntity!=null) {
				try {
					//利用从HttpEntity中得到的String生成JsonObject
					json=new JSONObject(EntityUtils.toString(entity));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		get.releaseConnection();
		return json;
	}
	
	public static Map<String,String> getLangdetect(String query) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		
		String url = "http://fanyi.baidu.com/langdetect?query=" + query;
		HttpGet get = new HttpGet(url);
		HttpResponse response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
		JSONObject json = null;
		int statusCode=response.getStatusLine().getStatusCode();
		if (statusCode==HttpStatus.SC_OK) {
			//得到httpResponse的实体数据
			HttpEntity httpEntity=response.getEntity();
			if (httpEntity!=null) {
				try {
					//利用从HttpEntity中得到的String生成JsonObject
					json=new JSONObject(EntityUtils.toString(entity));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		get.releaseConnection();
		if(json != null){
//			String error = json.getString("error");
			String msg = json.getString("msg");
			String lan = json.getString("lan");
			
//			System.out.println(error);
			System.out.println(msg);
			System.out.println(lan);
			
//			map.put("error", error);
			map.put("msg", msg);
			map.put("lan", lan);
		}
		return map;
	}
	
	public static Map<String,String> getTrans_result(JSONObject json) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		JSONObject trans_result = json.getJSONObject("trans_result");
		JSONArray data = (JSONArray) trans_result.get("data");
//		System.out.println(data.get(0));
		JSONObject result = (JSONObject) data.get(0);
		String dst = result.getString("dst");
		String src = result.getString("src");

		
		map.put("dst", dst);
		map.put("src", src);
		System.out.println(dst);
		System.out.println(src);
		return map;
	}
	
	@SuppressWarnings("unused")
	public static Map<String,String> getDict_result(JSONObject json) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		JSONObject dict_result = json.getJSONObject("dict_result");
		
		JSONObject simple_means = dict_result.getJSONObject("simple_means");
		
		String word_name = simple_means.getString("word_name");
		JSONArray word_means = simple_means.getJSONArray("word_means");
		JSONArray symbols = simple_means.getJSONArray("symbols");
		
		JSONObject result = (JSONObject) symbols.get(0);
		String word_symbol = result.getString("word_symbol");
		String symbol_mp3 = result.getString("symbol_mp3");
		
		System.out.println(word_name);
		System.out.println(word_symbol);
		System.out.println(symbol_mp3);
		
		map.put("word_name", word_name);
		map.put("word_symbol", word_symbol);
		map.put("symbol_mp3", symbol_mp3);
		return map;
	}
}
