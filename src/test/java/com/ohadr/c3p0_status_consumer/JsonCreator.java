package com.ohadr.c3p0_status_consumer;

import java.util.ArrayList;
import org.junit.Test;
import com.ohadr.c3p0_status_consumer.utils.Utils;
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
		String connectionPoolStatusCollectionJson = Utils.convertToJson( connectionPoolStatusCollection );
		System.out.println(connectionPoolStatusCollectionJson);
	}

}
