package com.ohadr.c3p0_status_consumer;

import java.util.Timer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import com.ohadr.c3p0_status_consumer.MonitorConnectionPoolsTask;


@Component
public class C3P0StatusConsumer implements InitializingBean
{
	private static Logger log = Logger.getLogger(C3P0StatusConsumer.class);


	@Override
	public void afterPropertiesSet() throws Exception
	{
		Timer t = new Timer();
		MonitorConnectionPoolsTask mTask = new MonitorConnectionPoolsTask();
		// This task is scheduled to run every 10 seconds

		log.info("starting");
		t.scheduleAtFixedRate(mTask, 0, 10000);

	}


	public void start()
	{
		Timer t = new Timer();
		MonitorConnectionPoolsTask mTask = new MonitorConnectionPoolsTask();
		// This task is scheduled to run every 10 seconds

		t.scheduleAtFixedRate(mTask, 0, 10000);

	}

	

}