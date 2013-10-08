package in.co.sh00nya.server.simplemt;

import in.co.sh00nya.cmn.DataProcessUnit;

import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;

public class SimpleMTWorker implements Runnable {
	
	private static final Logger logger = Logger.getLogger(SimpleMTWorker.class);
	
	private Socket clientSock = null;

	public SimpleMTWorker(Socket clientSock) {
		this.clientSock = clientSock;
	}

	@Override
	public void run() {
		// Start reading data from client
		try {
			DataProcessUnit.processData(clientSock.getInputStream(), clientSock.getOutputStream());
		} catch (IOException e) {
			logger.error("Failed to read from client stream", e);
			return;
		} finally {
			try {
				clientSock.close();
			} catch (IOException e) {
				logger.warn("Failed to client connection", e);
			}
		}
	}

}
