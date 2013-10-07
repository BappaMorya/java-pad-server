package in.co.sh00nya.client;

import in.co.sh00nya.cmn.RefConst;

public class ClientConfig {
	
	private String serverHost;
	
	private int serverPort = RefConst.DEFAULT_SERVER_PORT;
	
	private int noOfThreads;
	
	private int reqPerThread = RefConst.DEFAULT_REQ_PER_THREAD;
	
	private boolean syncFire = false;

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getNoOfThreads() {
		return noOfThreads;
	}

	public void setNoOfThreads(int noOfThreads) {
		this.noOfThreads = noOfThreads;
	}

	public int getReqPerThread() {
		return reqPerThread;
	}

	public void setReqPerThread(int reqPerThread) {
		this.reqPerThread = reqPerThread;
	}

	public boolean isSyncFire() {
		return syncFire;
	}

	public void setSyncFire(boolean syncFire) {
		this.syncFire = syncFire;
	}

	@Override
	public String toString() {
		return "ClientConfig [serverHost=" + serverHost + ", serverPort="
				+ serverPort + ", noOfThreads=" + noOfThreads
				+ ", reqPerThread=" + reqPerThread + ", syncFire=" + syncFire
				+ "]";
	}

}
