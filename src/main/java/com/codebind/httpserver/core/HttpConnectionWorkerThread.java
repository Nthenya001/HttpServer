package com.codebind.httpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 
public class HttpConnectionWorkerThread extends Thread{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
	private Socket socket;
	public HttpConnectionWorkerThread(Socket socket) {
		this.socket=socket;
	}
	@Override
	public void run() {
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			inputStream = socket.getInputStream();
			
			//writing to the socket
			
			outputStream =socket.getOutputStream();
			
			//we only use the ServerSocket to accept the connection. Once the connection is accepted we use the Socket.
			//we the inputStream that we get from the socket to read the bytes that are coming in.
			/**int _byte;
			
			while((_byte= inputStream.read()) >=0) {
				System.out.print((char) _byte);
			}**/
			
			
			//TODO we would write
			String html = "<html><head><title>Simple Java HTTP server</title></head><body><h1>This page was served using my simple Java HTTP server</<h1></body< </html>";
			// if we were to send the html like this to the browser,the browser wouldn't know what to do with it.
			// The reason is it's not wrapped in some kind of http response.
			
			//We need to send to special characters. i.e CARRIAGE_RETURN and LINE_FEED.
			// Signify an end of Line (EOL) marker
			
			final String CRLF = "\r\n";// 13,10 in ASCII
			
			String response = 
					"HTTP/1.1 200 OK" + CRLF +//Status line : HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
					"Content-length: "+ html.getBytes().length + CRLF + //HEADER- pass info about the response that can't be placed in the status line.Return the size of the message we want to send
						CRLF +// to show we are done with the header
						html +
						CRLF + CRLF ;
			
			outputStream.write(response.getBytes());
			
			
			
			LOGGER.info("Connection processing Finished");
		} catch (IOException e) {
			LOGGER.error("Problem with communication", e);
			e.printStackTrace();
		} finally {
			if (inputStream !=null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {

				}
			}
			
			if (socket !=null) {
				try {
					socket.close();
				} catch (IOException e) {

				}
			}
			
			
		}
	}

}

		