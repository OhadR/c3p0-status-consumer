package com.ohadr.c3p0_status_consumer.web;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller("consumerWebController")
public class WebController 
{
	private static Logger log = Logger.getLogger(WebController.class);


    @RequestMapping("/ping")	
	protected void ping(
			HttpServletResponse response) throws Exception{
		log.info( "got to ping" );
		response.getWriter().println("ping response: pong");
	}
}