package com.ohadr.c3p0_status_consumer;

import java.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.ohadr.c3p0_status_consumer.config.PropertiesResolver;
import com.ohadr.c3p0_status_consumer.utils.Utils;
import com.who.tlv.foundation.csv.ICSVWriter;
import com.who.tlv.mars.common.ConnectionPoolStatus;
import com.who.tlv.mars.common.ConnectionPoolStatusCollection;

@Component
public class MonitorConnectionPoolsTask extends TimerTask
{
	private static final int READ_TIME_OUT = 100000;
	private static final int CONNECTION_TIME_OUT = 100000;
	private static final String GET_CONN_POOL_STATUS_URL = "/connPoolStatus";
	
	private static Logger log = Logger.getLogger(MonitorConnectionPoolsTask.class);
	private RestTemplate restTemplate = createRestTemplate();

	@Autowired
	private PropertiesResolver properties;
	
	private ICSVWriter csvFile;
	
	private RestTemplate createRestTemplate()
	{
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setReadTimeout(READ_TIME_OUT);
		requestFactory.setConnectTimeout(CONNECTION_TIME_OUT);
		return new RestTemplate(requestFactory);
	}

	public void getConnPoolStatus()
	{
		log.info("get Conn-Pool Status from " + properties.getTargetHost() + "...");
		String jsonResult = restTemplate.getForObject(properties.getTargetHost() + GET_CONN_POOL_STATUS_URL, String.class);
		log.info("result: " + jsonResult);
		
		ConnectionPoolStatusCollection connectionPoolStatus = Utils.convertFromJson( jsonResult, ConnectionPoolStatusCollection.class );
		for(ConnectionPoolStatus cps : connectionPoolStatus.collection)
		{
			List<String> line = new ArrayList<String>();
			line.add( cps.dataSourceName );
			line.add( new Date().toString() );
			line.add( String.valueOf( cps.numConnections ) );
			line.add( String.valueOf( cps.numConnectionsAllUsers ) );
			line.add( String.valueOf( cps.numIdleConnections ) );
			line.add( String.valueOf( cps.numIdleConnectionsAllUsers ) );
			line.add( String.valueOf( cps.numBusyConnections ) );
			line.add( String.valueOf( cps.numBusyConnectionsAllUsers ) );
			line.add( String.valueOf( cps.numThreadsAwaitingCheckoutDefaultUser ) );
			line.add( String.valueOf( cps.numUnclosedOrphanedConnections ) );

			csvFile.writeLine( line );
			log.debug("*** " + cps);
		}
		
	}

	@Override
	public void run()
	{
		getConnPoolStatus();
		System.out.println("Hi see you after 10 seconds");
	}

	public void setCsvFile(ICSVWriter csvFile)
	{
		this.csvFile = csvFile;
	}

}