package in.co.sh00nya.server;

import in.co.sh00nya.cmn.RefConst;

/**
 * Represents server configuration. This will be passed around to all servers 
 * No one knows where is this populated from ;)
 * @author siddharth
 *
 */
public class ServerConfig {
	
	private String hostname;
	
	private int port = RefConst.DEFAULT_SERVER_PORT;

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "ServerConfig [hostname=" + hostname + ", port=" + port + "]";
	}

}
