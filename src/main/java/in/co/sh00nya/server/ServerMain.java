package in.co.sh00nya.server;

import in.co.sh00nya.cmn.ConfigException;
import in.co.sh00nya.cmn.PropsPojoAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ServerMain {
	
	private static final Logger logger = Logger.getLogger(ServerMain.class);

	public static void main(String[] args) {
		
		logger.info("Java Pad Server booting up ...");
		
		logger.info("Loading configuration ...");
		
		Properties props = new Properties();
		InputStream ins = ServerMain.class.getClassLoader().getResourceAsStream("server_config.properties");
		if(ins != null) {
			try {
				props.load(ins);
			} catch (IOException e) {
				logger.error("Failed to load server_config.properties", e);
				System.exit(-1);
			} finally {
				if(ins != null)
					try {
						ins.close();
					} catch (IOException e) {
						// Ignore this problem
					}
			}
		} else {
			logger.error("Failed to find server_config.properties");
			System.exit(-1);
		}
		
		PropsPojoAdapter<ServerConfig> pojoAdpt = new PropsPojoAdapter<ServerConfig>(false);
		ServerConfig cfg = null;
		
		try {
			cfg = pojoAdpt.transformProps(props, ServerConfig.class);
		} catch (ConfigException e) {
			logger.error("Failed to parse server_config.properties", e);
			System.exit(-1);
		}
		
		IServer server = null;
		
		try {
			Class<IServer> handlerClass = (Class<IServer>) Class.forName(cfg.getServerHandlerClass());
			server = handlerClass.newInstance();
			server.setConfig(cfg);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			logger.error("Failed to load server handler class", e);
			System.exit(-1);
		}
		
		// Register shutdown trigger
		logger.info("Registering shutdown hook ...");
		ServerShutdownTrigger shutdownTrigger = new ServerShutdownTrigger();
		shutdownTrigger.setServer(server);
		Thread shutdownThread = new Thread(shutdownTrigger);
		Runtime.getRuntime().addShutdownHook(shutdownThread);
		
		// Start server thread
		logger.info("Starting server ...");
		ServerMT serverMt = new ServerMT();
		serverMt.setCfg(cfg);
		serverMt.setServer(server);
		
		Thread serverThread = new Thread(serverMt);
		serverThread.start();
		try {
			serverThread.join();
		} catch (InterruptedException e) {
			logger.error("Failed to wait for server thread to complete", e);
			System.exit(-1);
		}
		
	}

}


 