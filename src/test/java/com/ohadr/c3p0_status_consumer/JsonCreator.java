package com.ohadr.c3p0_status_consumer;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import com.ohadr.common.utils.JsonUtils;
import com.who.tlv.mars.common.ConnectionPoolStatus;
import com.who.tlv.mars.common.ConnectionPoolStatusCollection;

public class JsonCreator
{

	@Test
	public void test()
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

}
