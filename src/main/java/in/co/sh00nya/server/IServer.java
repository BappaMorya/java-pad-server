package in.co.sh00nya.server;

import in.co.sh00nya.cmn.ServerException;

/**
 * Represents server lifecycle. This consists of following: <br />
 * 1. Set server configuration <br />
 * 2. Bind server <br />
 * 3. Start accepting incoming connection <br />
 * 4. Close server <br />
 * 5. Get server stats <br />
 * 
 * @author siddharth
 *
 */
public interface IServer {
	
	/**
	 * Sets server configuration on a server
	 * @param serverConfig Server configuration
	 */
	public void setConfig(ServerConfig serverConfig);
	
	/**
	 * Binds server using server configuration
	 * @throws ServerException
	 */
	public void bindServer() throws ServerException;
	
	/**
	 * Start accepting client connections
	 * @throws ServerException
	 */
	public void startServer() throws ServerException;
	
	/**
	 * Stops server
	 * @throws ServerException
	 */
	public void stopServer() throws ServerException;
	
	// TODO Add stats handling part
	
}
