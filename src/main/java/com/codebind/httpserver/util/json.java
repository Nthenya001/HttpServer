package com.codebind.httpserver.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class json {
	/**
	* ObjectMapper provides functionality for reading and writing Json, either to and from basic POJOs(Plain Old java Objects)
	* or to and from a general purpose JSON tree model
	**/

	//Static methods/attributes can be accessed without creating an object of a class.
	private static ObjectMapper myObjectMapper= defaultObjectMapper();
	
	//method that creates a new objectMapper
	
	private static ObjectMapper defaultObjectMapper() {
		ObjectMapper om = new  ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// Makes a parsing not crush incase there is one property missing
		return om; 
	}
	
	
	//parsing Json string into JsonNode
	public static JsonNode parse(String jsonsrc) throws IOException {
		return myObjectMapper.readTree(jsonsrc);
	}
	
	//moving the JsonNode into the Configuration podium.
	//we don't know what type it is going to return so we create a generic one.
	public static <A> A /**generic type **/ fromJson/** method**/(JsonNode node, Class <A> clazz)/**the class we want to transform it into**/ throws JsonProcessingException, IllegalArgumentException {
		return myObjectMapper.treeToValue(node, clazz);
	}
	
	//a way of getting the configuration file into JsonNode
	public static JsonNode toJson (Object obj) {
		return myObjectMapper.valueToTree(obj);
	}
	
	public static String stringify(JsonNode node) throws JsonProcessingException {
		return generateJson(node, false);
	}
	
	public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
		return generateJson(node, true);
	}
	
	// Json to string format
	private static String generateJson(Object o, boolean pretty) throws JsonProcessingException {
		ObjectWriter objectWriter = myObjectMapper.writer();
		
		if (pretty) {
			objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
		}
		return objectWriter.writeValueAsString(o);
	}
	
}
 

		

