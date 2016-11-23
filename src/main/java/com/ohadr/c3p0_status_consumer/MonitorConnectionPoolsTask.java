package com.ohadr.c3p0_status_consumer;

import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class MonitorConnectionPoolsTask extends TimerTask
{
	private static final int READ_TIME_OUT = 100000;
	private static final int CONNECTION_TIME_OUT = 100000;
//	private static final String GET_CONN_POOL_STATUS_URL = "/Rest/connPoolStatus";
	private static final String GET_CONN_POOL_STATUS_URL = "/connPoolStatus";
	
	private static Logger log = Logger.getLogger(MonitorConnectionPoolsTask.class);
	private RestTemplate restTemplate = createRestTemplate();

	
	private RestTemplate createRestTemplate()
	{
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setReadTimeout(READ_TIME_OUT);
		requestFactory.setConnectTimeout(CONNECTION_TIME_OUT);
		return new RestTemplate(requestFactory);
	}

	public void getConnPoolStatus()
	{
		String result = restTemplate.getForObject("http://localhost:8080/c3p0-test/" + GET_CONN_POOL_STATUS_URL, String.class);
	}

	@Override
	public void run()
	{
		System.out.println("Hi see you after 10 seconds");
	}

}
