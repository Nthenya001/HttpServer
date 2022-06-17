package com.codebind.httpserver.config1;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.codebind.httpserver.config1.Configuration;
import com.codebind.httpserver.config1.HttpConfigurationException;
import com.codebind.httpserver.util.json;


public class ConfigurationManager {
	private static ConfigurationManager myConfigurationManager;//variable
	private static Configuration myCurrentConfiguration;
	// constructor
	private ConfigurationManager() {
		
	}
	
	//create the get instance
	
	public static ConfigurationManager getInstance() {
		if(myConfigurationManager==null)
			myConfigurationManager= new ConfigurationManager();
		return myConfigurationManager;
		
	}
	
	// a method to load files. 
	// Used to load a configuration file by the path provided
	


	public void loadConfigurationFile(String filePath){
		FileReader fileReader;
		try {
			fileReader = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			throw new HttpConfigurationException(e);
		}
		/**Java FileReader class is used to read data from the file.
		 *  It returns data in byte format like FileInputStream class 
		 *  **/
		StringBuffer sb = new StringBuffer();
		/** StringBuffer class is used to create mutable (modifiable) String objects. The StringBuffer 
		 * class in Java is the same as String class except it is mutable i.e. it can be changed. 
		 **/
		int i;
		try {
			while ((i = fileReader.read()) !=-1) {
				sb.append((char) i);
			}
		} catch (IOException e) {
			throw new HttpConfigurationException(e);
		}
		
		JsonNode conf;
		try {
			conf = json.parse(sb.toString());
		} catch (IOException e) {
			throw new HttpConfigurationException("Error Parsing the Configuration file", e);
		}
		try {
			myCurrentConfiguration = json.fromJson(conf, Configuration.class);
		} catch (JsonProcessingException e) {
			throw new HttpConfigurationException("Error Parsing the Configuration file, Internal",e);

		}
	}
	
	//methods to get the configuration thats available.
	//Returns the current loaded configuration
	
	public Configuration getCurrentConfiguration() {
		if (myCurrentConfiguration ==null) {
			throw new HttpConfigurationException("No Current Configuration Set");
		}
		return myCurrentConfiguration;
	}
}


