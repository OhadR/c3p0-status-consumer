package com.ohadr.c3p0_status_consumer.utils;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class Utils
{
	private static Logger log = Logger.getLogger(Utils.class);

	public static <T> T convertFromJson(String json, Class<T> typeParameterClass)
	{
	    ObjectMapper mapper = new ObjectMapper();
	    
	    T t = null;
		try
		{
			t = (T) mapper.readValue(json, typeParameterClass);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
	        log.error("error converting " + json + " from JSON");
		}
	    return t;
	}

}
