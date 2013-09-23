package in.co.sh00nya.server;

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
	
	public void setConfig(ServerConfig serverConfig);

}
