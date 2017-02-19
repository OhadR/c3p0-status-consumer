package com.ohadr.c3p0_status_consumer;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;
import com.ohadr.common.types.c3p0.ConnectionPoolStatus;
import com.ohadr.common.types.c3p0.ConnectionPoolStatusCollection;
import com.ohadr.common.utils.JsonUtils;

public class JsonCreator
{
	@Test
	public void testCreateJsonOfConnectionPoolStatusCollection()
	{
		ConnectionPoolStatusCollection connectionPoolStatusCollection = new ConnectionPoolStatusCollection();
		connectionPoolStatusCollection.collection = new ArrayList<ConnectionPoolStatus>();
		ConnectionPoolStatus status = new ConnectionPoolStatus();
		status.dataSourceName = "testDS";
		connectionPoolStatusCollection.collection.add(status);
		String connectionPoolStatusCollectionJson = JsonUtils.convertToJson( connectionPoolStatusCollection );
		System.out.println(connectionPoolStatusCollectionJson);
		
		String expectedJsonResult = 
		"{\"collection\":[{\"numBusyConnections\":0,\"numBusyConnectionsAllUsers\":0,\"numIdleConnections\":0,\"numIdleConnectionsAllUsers\":0,\"numConnections\":0,\"numConnectionsAllUsers\":0,\"numThreadsAwaitingCheckoutDefaultUser\":0,\"numUnclosedOrphanedConnections\":0,\"dataSourceName\":\"testDS\"}]}";
		
		Assert.assertEquals(expectedJsonResult, connectionPoolStatusCollectionJson);
	}
	
	
	@Test
	public void testFileRolling()
	{
		Date now = new Date();
		long msecNow = now.getTime();
		int NUM_DAYS = 6;
		long msecsXdaysAgo = msecNow - TimeUnit.DAYS.toMillis( NUM_DAYS ) ;
		Date twoDaysAgoDate = new Date( msecsXdaysAgo ); 

		if( twoDaysAgoDate.before(now) )
		{
			//create a new file:
			System.out.println("rolling!");
			
//			ICSVWriter csvFile = fileAndFileCreationDatePair.getLeft();
//			csvFile.close();
//			csvFile = createCsvFileForDataSource( cps.dataSourceName, now );
		}	
		else
		{
			Assert.assertTrue(false);
		}
		
	}

}
