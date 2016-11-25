package com.ohadr.c3p0_status_consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesResolver
{
//	@Value("${com.ohadr.auth-flows.linksExpirationMinutes}")
	private int linksExpirationMinutes;

//	@Value("${com.ohadr.auth-flows.isREST}")
	private boolean isREST;
	
	@Value("${com.ohadr.c3p0-status-consumer.target-url}")
	private String targetHost;
	
	@Value("${com.ohadr.c3p0-status-consumer.history-file}")
	private String historyFileName;
	
	public int getLinksExpirationMinutes()
	{
		return linksExpirationMinutes;
	}

	public void setLinksExpirationMinutes(int linksExpirationMinutes)
	{
		this.linksExpirationMinutes = linksExpirationMinutes;
	}
	
	public boolean isREST() 
	{
		return isREST;
	}
	
	public String getTargetHost()
	{
		return targetHost;
	}

	public String getHistoryFileName()
	{
		return historyFileName;
	}


}


