package in.co.sh00nya.server.simple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.log4j.Logger;

import in.co.sh00nya.cmn.DataProcessUnit;
import in.co.sh00nya.cmn.ServerException;
import in.co.sh00nya.server.IServer;
import in.co.sh00nya.server.ServerConfig;

public class SimpleSocketServer implements IServer {
	
	private static final Logger logger = Logger.getLogger(SimpleSocketServer.class);
	
	private volatile boolean isRunning = false;
	
	private ServerConfig serverConfig = null;
	
	private ServerSocket serverSock = null;

	public void setConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	public void bindServer() throws ServerException {
		SocketAddress sockAddr = new InetSocketAddress(serverConfig.getBindIpAddress(), 
				serverConfig.getBindPort());
		logger.info("Creating server at " + serverConfig);
		try {
			serverSock = new ServerSocket();
			serverSock.bind(sockAddr);
			isRunning = true;
		} catch (IOException e) {
			logger.error("Failed to start server", e);
			throw new ServerException("Failed to start server", e);
		}
	}

	public void startServer() throws ServerException {
		while (isRunning) {
			Socket clientSock = null;
			try {
				logger.info("Starting to accept client connections ...");
				clientSock = serverSock.accept();
				logger.debug("Accepted connection from " + clientSock);
			} catch (IOException e) {
				logger.error("Failed to accept client connection", e);
				stopServer();
				throw new ServerException("Failed to accept client connection", e);
			}
			
			if(clientSock != null) {
				// Start reading data from client
				try {
					DataProcessUnit.processData(clientSock.getInputStream(), clientSock.getOutputStream());
				} catch (IOException e) {
					logger.error("Failed to read from client stream", e);
					throw new ServerException("Failed to read from client stream", e);
				} finally {
					try {
						clientSock.close();
					} catch (IOException e) {
						logger.warn("Failed to client connection", e);
					}
				}
			}
		}
	}

	public void stopServer() throws ServerException {
		if(serverSock != null) {
			logger.info("Shutting down server ...");
			try {
				serverSock.close();
			} catch (IOException e) {
				logger.error("Failed to shut down server ...", e);
			}
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
}
