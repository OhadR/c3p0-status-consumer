package com.ohadr.c3p0_status_consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesResolver
{
//	@Value("${com.ohadr.c3p0-status-consumer.test-mode}")
//	private boolean testMode;
	
	@Value("${com.ohadr.c3p0-status-consumer.target-url}")
	private String targetHost;

	@Value("${com.ohadr.c3p0-status-consumer.num-days-for-file}")
	private int numDaysForFile;

	//defines the interval between each sample, in seconds
	@Value("${com.ohadr.c3p0-status-consumer.sample-rate-seconds}")
	private int sampleRateSeconds;
	

	public String getTargetHost()
	{
		return targetHost;
	}

	public int getNumDaysForFile()
	{
		return numDaysForFile;
	}

	public int getSampleRateSeconds()
	{
		return sampleRateSeconds;
	}

}


