package in.co.sh00nya.server.simple;

import in.co.sh00nya.cmn.ServerException;
import in.co.sh00nya.server.IServer;
import in.co.sh00nya.server.ServerConfig;

public class SimpleSocketServer implements IServer {
	
	private volatile boolean isRunning = true;
	
	private ServerConfig serverConfig = null;

	@Override
	public void setConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	@Override
	public void bindServer() throws ServerException {
	}

	@Override
	public void startServer() throws ServerException {
	}

	@Override
	public void stopServer() throws ServerException {
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
}
