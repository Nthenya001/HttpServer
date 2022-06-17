package com.codebind.httpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//this thread is for accepting the connection.
public class ServerListenerThread extends Thread {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);
	
	private int port;
	private String WebRoot;
	private ServerSocket serverSocket;
	
	//constructor
	public ServerListenerThread(int port, String webRoot) throws IOException {
		this.port = port;
		this.WebRoot = webRoot;
		this.serverSocket = new ServerSocket(this.port);
	}




	@Override
	public void run() {
		
		
		//TCP SERVER SOCKETS
		
				/**
				 * ServerSocket is going to listen to a specific port. create the serverSocket and 
				 * pass to the serverSocket the port that we gather from the configuration manager. 
				 */
				
try {
			//The isBound() method returns a Boolean value 'true' if the ServerSocket successfully bound to an address.
			//The isClosed() method returns a Boolean value 'true' if the socket has been closed.
	while(serverSocket.isBound() && !serverSocket.isClosed()) { // we keep on accepting the connection
		
			Socket socket = serverSocket.accept(); // It prompts the socket thats listening to a port to accept any connection that arise.
			// the code stops at accept() and stays there waiting for the connection.
			
			//reading from the socket
			
			LOGGER.info("Connection accepted:" + socket.getInetAddress());// get address to which the socket is connected.
			
			HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
			workerThread.start();
	}
			
		} catch (IOException e) {
			LOGGER.error("Problem with setting socket", e);
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {}
			}
		}
	}

}
 

		
