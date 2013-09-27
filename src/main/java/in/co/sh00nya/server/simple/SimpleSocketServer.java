package in.co.sh00nya.server.simple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;

import in.co.sh00nya.cmn.ServerException;
import in.co.sh00nya.server.IServer;
import in.co.sh00nya.server.ServerConfig;

public class SimpleSocketServer implements IServer {
	
	private volatile boolean isRunning = false;
	
	private ServerConfig serverConfig = null;
	
	private ServerSocket serverSock = null;

	public void setConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	public void bindServer() throws ServerException {
		SocketAddress sockAddr = new InetSocketAddress(serverConfig.getHostname(), 
				serverConfig.getPort());
		try {
			serverSock = new ServerSocket();
			serverSock.bind(sockAddr);
			isRunning = true;
		} catch (IOException e) {
			// TODO error code handling
			e.printStackTrace();
		}
	}

	public void startServer() throws ServerException {
	}

	public void stopServer() throws ServerException {
		if(isRunning) {
			System.out.println("Shutting down server ...");
			try {
				serverSock.close();
			} catch (IOException e) {
				e.printStackTrace();
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
