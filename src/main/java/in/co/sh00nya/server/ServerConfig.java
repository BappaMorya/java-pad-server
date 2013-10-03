package in.co.sh00nya.server;

import in.co.sh00nya.cmn.RefConst;

/**
 * Represents server configuration. This will be passed around to all servers 
 * No one knows where is this populated from ;)
 * @author siddharth
 *
 */
public class ServerConfig {
	
	private String bindIpAddress = RefConst.DEFAULT_SERVER_BIND_ADDRESS;
	
	private int bindPort = RefConst.DEFAULT_SERVER_PORT;
	
	private String serverHandlerClass = null;

	public String getBindIpAddress() {
		return bindIpAddress;
	}

	public void setBindIpAddress(String bindIpAddress) {
		this.bindIpAddress = bindIpAddress;
	}

	public int getBindPort() {
		return bindPort;
	}

	public void setBindPort(int bindPort) {
		this.bindPort = bindPort;
	}

	public String getServerHandlerClass() {
		return serverHandlerClass;
	}

	public void setServerHandlerClass(String serverHandlerClass) {
		this.serverHandlerClass = serverHandlerClass;
	}

	@Override
	public String toString() {
		return "ServerConfig [bindIpAddress=" + bindIpAddress + ", bindPort="
				+ bindPort + ", serverHandlerClass=" + serverHandlerClass + "]";
	}

}
