package com.codebind.httpserver;



import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.codebind.httpserver.config1.Configuration;
import com.codebind.httpserver.config1.ConfigurationManager;
import com.codebind.httpserver.core.ServerListenerThread;

public class HttpServer {
	
/**driver class for HTTP server **/
	
	/**
	 * We create the Logger instance by using the LoggerFactory class and 
	 * its getLogger method and providing the name of the class.
	 * That way we bind the logger to the class name which gives us the context of the log message.
	 **/
	private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
	
	public static void main(String[] args) {
		LOGGER.info("Server starting...");
		
		// to start using the configuration manager we just call it
		ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
		Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
		
		LOGGER.info("Using Port:" + conf.getPort());
		LOGGER.info("Using WebROOT:" + conf.getWebroot());
		
		
		
		try {
		ServerListenerThread serverListenerThread = new ServerListenerThread (conf.getPort(), conf.getWebroot());
		serverListenerThread.start();
		} catch (IOException e) {
			e.printStackTrace();
			//TODO handle later.
		}
		
	}
	

}
