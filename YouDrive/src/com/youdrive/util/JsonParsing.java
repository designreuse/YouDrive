package com.youdrive.util;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;

import org.json.JSONObject;

public class JsonParsing {

    public JSONObject getErrorCodes() throws Exception{
    	//Can also use ServletContext and call getResourceAsStream when in servlet
		//InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("json/mysql_errorcodes.json");
		InputStream is = this.getClass().getResourceAsStream("mysql_errorcodes.json");
    	String jsonTxt = IOUtils.toString(is);
		JSONObject json = new JSONObject(jsonTxt); 
		return json;
    }
    
    public static JSONObject getErrorCodes(ServletContext ctx) throws Exception{
    	//Can also use ServletContext and call getResourceAsStream when in servlet
		InputStream is = ctx.getResourceAsStream("json/mysql_errorcodes.json");
		String jsonTxt = IOUtils.toString(is);
		JSONObject json = new JSONObject(jsonTxt); 
		return json;
    }
    
    public JsonParsing(){  }
}

