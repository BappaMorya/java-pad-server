package in.co.sh00nya.server;

import org.apache.log4j.Logger;

import in.co.sh00nya.cmn.ServerException;

public class ServerMT implements Runnable {
	
	private static final Logger logger = Logger.getLogger(ServerMT.class);
	
	private ServerConfig cfg;
	
	private IServer server;

	public void setServer(IServer server) {
		this.server = server;
	}

	public IServer getServer() {
		return server;
	}

	public ServerConfig getCfg() {
		return cfg;
	}

	public void setCfg(ServerConfig cfg) {
		this.cfg = cfg;
	}

	@Override
	public void run() {
		
		// Bind server to configured host:port address
		try {
			server.bindServer();
		} catch (ServerException e) {
			logger.error("Failed to bind server to " + cfg.getBindIpAddress() 
					+ ":" + cfg.getBindPort(), e);
			stopServer(e);
			return;
		}

		try {
			server.startServer();
		} catch (ServerException e) {
			logger.error("Failed to start server", e);
			stopServer(e);
			return;
		}
	}
	
	public void stopServer(Exception e) {
		try {
			server.stopServer();
		} catch (ServerException e1) {
			// ignore this failure
		}
	}

}
