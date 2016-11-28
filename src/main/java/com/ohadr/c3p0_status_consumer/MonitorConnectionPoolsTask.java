package com.ohadr.c3p0_status_consumer;

import java.io.File;
import java.util.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import com.ohadr.c3p0_status_consumer.config.PropertiesResolver;
import com.ohadr.c3p0_status_consumer.utils.Utils;
import com.who.tlv.foundation.csv.CSVWriter;
import com.who.tlv.foundation.csv.ICSVWriter;
import com.who.tlv.mars.common.ConnectionPoolStatus;
import com.who.tlv.mars.common.ConnectionPoolStatusCollection;

@Component
public class MonitorConnectionPoolsTask extends TimerTask
{
	private static final int READ_TIME_OUT = 100000;
	private static final int CONNECTION_TIME_OUT = 100000;
	private static final String GET_CONN_POOL_STATUS_URL = "/connPoolStatus";
	private static final String PATH_TO_TOMCAT_WORK_DIR = System.getProperty("user.dir") + File.separator + "work" + File.separator;

	private static Logger log = Logger.getLogger(MonitorConnectionPoolsTask.class);
	private RestTemplate restTemplate = createRestTemplate();

	@Autowired
	private PropertiesResolver properties;
	
	//map from the file name to a pair of <CSV file object, file creation date> :
	private Map<String, Pair<ICSVWriter, Date> > dataSourceNameToFileMap = new HashMap<String, Pair<ICSVWriter, Date> >();
	
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
		String jsonResult = null;
		try
		{
			jsonResult = restTemplate.getForObject(properties.getTargetHost() + GET_CONN_POOL_STATUS_URL, String.class);
		}
		catch(ResourceAccessException rae)
		{
			log.error("error getting response from remote host (" + properties.getTargetHost() + ")", rae);
			if(properties.isTestMode())
				jsonResult = 
				"{\"collection\":[{\"numBusyConnections\":0,\"numBusyConnectionsAllUsers\":0,\"numIdleConnections\":0,\"numIdleConnectionsAllUsers\":0,\"numConnections\":0,\"numConnectionsAllUsers\":0,\"numThreadsAwaitingCheckoutDefaultUser\":0,\"numUnclosedOrphanedConnections\":0,\"dataSourceName\":\"testDS\"}]}";	
			else
				throw rae;
		}
		log.info("result: " + jsonResult);
		
		ConnectionPoolStatusCollection connectionPoolStatus = Utils.convertFromJson( jsonResult, ConnectionPoolStatusCollection.class );
		for(ConnectionPoolStatus cps : connectionPoolStatus.collection)
		{
			Pair<ICSVWriter, Date> fileAndFileCreationDatePair = dataSourceNameToFileMap.get( cps.dataSourceName );
			Date now = new Date();
			if(fileAndFileCreationDatePair == null)
			{
				ICSVWriter csvFile = createCsvFileForDataSource( cps.dataSourceName, now );
				fileAndFileCreationDatePair = Pair.of(csvFile, now);
				dataSourceNameToFileMap.put(cps.dataSourceName, fileAndFileCreationDatePair );
			}
			else
			{
				//check if need to create a new file (as max period elapsed):
				Date fileCreationDate = fileAndFileCreationDatePair.getRight();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime( fileCreationDate );
				calendar.add( Calendar.DAY_OF_MONTH, properties.getNumDaysForFile() );
				if( calendar.before(now) )
				{
					//create a new file:
					ICSVWriter csvFile = fileAndFileCreationDatePair.getLeft();
					csvFile.close();
					csvFile = createCsvFileForDataSource( cps.dataSourceName, now );
				}				
			}
			
			
			List<String> line = new ArrayList<String>();
			line.add( new Date().toString() );
			line.add( String.valueOf( cps.numConnections ) );
			line.add( String.valueOf( cps.numIdleConnections ) );
			line.add( String.valueOf( cps.numBusyConnections ) );
			line.add( String.valueOf( cps.numConnectionsAllUsers ) );
			line.add( String.valueOf( cps.numIdleConnectionsAllUsers ) );
			line.add( String.valueOf( cps.numBusyConnectionsAllUsers ) );
			line.add( String.valueOf( cps.numThreadsAwaitingCheckoutDefaultUser ) );
			line.add( String.valueOf( cps.numUnclosedOrphanedConnections ) );

			ICSVWriter csvFile = fileAndFileCreationDatePair.getLeft();
			csvFile.writeLine( line );
			csvFile.flush();
			log.debug("*** " + cps);
		}
		
	}

	private static ICSVWriter createCsvFileForDataSource(String dataSourceName, Date now)
	{
		log.info("creating CSV file " + dataSourceName);

		ICSVWriter csvFile = new CSVWriter( PATH_TO_TOMCAT_WORK_DIR + dataSourceName + "_" + now.getTime() + ".csv" );
		String headerFields = "date,numConnections,numIdleConnections,numBusyConnections,numConnectionsAllUsers,numIdleConnectionsAllUsers,numBusyConnectionsAllUsers,numThreadsAwaitingCheckoutDefaultUser,numUnclosedOrphanedConnections";
		csvFile.writeLine( Arrays.asList(headerFields.split(",")) );
		return csvFile;
	}

	@Override
	public void run()
	{
		getConnPoolStatus();
	}
}