package com.ohadr.c3p0_status_consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesResolver
{
	@Value("${com.ohadr.c3p0-status-consumer.test-mode}")
	private boolean testMode;
	
	@Value("${com.ohadr.c3p0-status-consumer.target-url}")
	private String targetHost;

	@Value("${com.ohadr.c3p0-status-consumer.num-days-for-file}")
	private int numDaysForFile;
	

	public boolean isTestMode() 
	{
		return testMode;
	}
	
	public String getTargetHost()
	{
		return targetHost;
	}

	public int getNumDaysForFile()
	{
		return numDaysForFile;
	}

}


