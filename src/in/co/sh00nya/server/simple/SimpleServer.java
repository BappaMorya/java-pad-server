package in.co.sh00nya.server.simple;

import in.co.sh00nya.cmn.RefConst;
import in.co.sh00nya.cmn.ServerException;

public class SimpleServer {
	
	private volatile boolean isRunning = true;
	
	private String hostname;
	
	private int port = RefConst.DEFAULT_SERVER_PORT;

	public SimpleServer(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	public SimpleServer(int port) {
		this.port = port;
	}
	
	public void startServer() throws ServerException {
		
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public String getHostname() {
		return hostname;
	}

	public int getPort() {
		return port;
	}

}
