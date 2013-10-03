package in.co.sh00nya.server;

import org.apache.log4j.Logger;

import in.co.sh00nya.cmn.ServerException;

public class ServerShutdownTrigger implements Runnable {
	
	private static final Logger logger = Logger.getLogger(ServerShutdownTrigger.class);
	
	private IServer server;

	public IServer getServer() {
		return server;
	}

	public void setServer(IServer server) {
		this.server = server;
	}

	@Override
	public void run() {
		try {
			server.stopServer();
		} catch (ServerException e) {
			logger.error("Failed to stop server", e);
			return;
		}
	}

}
