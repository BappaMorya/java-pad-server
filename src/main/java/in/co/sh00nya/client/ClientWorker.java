package in.co.sh00nya.client;

import in.co.sh00nya.cmn.DataProcessUnit;
import in.co.sh00nya.cmn.DataStore;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

public class ClientWorker implements Runnable {
	
	private static final Logger logger = Logger.getLogger(ClientWorker.class);
	
	private int workerId;
	
	private ClientConfig cfg;
	
	private CyclicBarrier syncBarrier;
	
	private CountDownLatch completionTriggerLatch;
	
	// stats 
	
	private long acceptTime = 0L;
	
	private long processTime = 0L;
	
	private long firstRequestTime = 0L;
	
	private Random randIndexGen = new Random();
	
	private DataStore store = null;

	public long getFirstRequestTime() {
		return firstRequestTime;
	}

	public int getWorkerId() {
		return workerId;
	}

	public long getAcceptTime() {
		return acceptTime;
	}

	public long getProcessTime() {
		return processTime;
	}

	public void setSyncBarrier(CyclicBarrier syncBarrier) {
		this.syncBarrier = syncBarrier;
	}

	public void setCompletionTriggerLatch(CountDownLatch completionTriggerLatch) {
		this.completionTriggerLatch = completionTriggerLatch;
	}

	public ClientWorker(ClientConfig cfg, int workerId) {
		this.cfg = cfg;
		this.workerId = workerId;
		this.store = DataStore.getInstance();
	}

	@Override
	public void run() {
		
		logger.info("Initializing ...");
		
		Socket clientSock = new Socket();
		InputStreamReader ins = null;
		OutputStream ous = null;
		
		try {
			logger.info("Connecting to server ...");
			long startTime = System.currentTimeMillis();
			clientSock.connect(new InetSocketAddress(cfg.getServerHost(), cfg.getServerPort()));
			acceptTime = System.currentTimeMillis() - startTime;
			logger.info("Connected ...");
			
			if(cfg.isSyncFire()) {
				logger.info("Waiting for other workers to sync ...");
				try {
					syncBarrier.await();
				} catch (Exception e) {
					logger.error("Failed to wait for another workers", e);
					return;
				}
			}
			
			ins = new InputStreamReader(clientSock.getInputStream());
			ous = clientSock.getOutputStream();

			logger.info("Starting server fire cycle ...");
			
			int count = 0;
			long endTime = 0L;
			for(int i=0; i<cfg.getReqPerThread(); i++) {
				startTime = System.currentTimeMillis();
				ous.write(store.getDataLine(randIndexGen.nextInt(store.size())).getBytes());
				ous.write("$CHECK$".getBytes());
				ous.flush();
				count = readCount(ins);
				endTime = System.currentTimeMillis();
				processTime += (endTime - startTime);
				if(i == 0)
					firstRequestTime = endTime - startTime;
				logger.debug("Received count = " + count);
			}
			ous.write("%STOP%$CHECK$".getBytes());
			ous.flush();
		} catch (IOException e) {
			logger.error("Failed to get connection to server ...", e);
			completionTriggerLatch.countDown();
		} finally {
			try {
				if(ins != null)
					ins.close();
			} catch (IOException e1) {
				// Ignore
			}
			try {
				if(ous != null)
					ous.close();
			} catch (IOException e1) {
				// Ignore
			}
			try {
				clientSock.close();
			} catch (IOException e) {
				logger.warn("Failed to close client connection", e);
			}
			completionTriggerLatch.countDown();
		}
		
		logger.info("Finished firing cycle ...");
		
	}
	
	public int readCount(Reader reader) throws IOException {
		return Integer.parseInt(DataProcessUnit.readUntil(reader, "$"));
	}

}
