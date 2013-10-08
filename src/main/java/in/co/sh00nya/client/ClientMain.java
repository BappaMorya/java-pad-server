package in.co.sh00nya.client;

import in.co.sh00nya.cmn.ConfigException;
import in.co.sh00nya.cmn.DataStore;
import in.co.sh00nya.cmn.PropsPojoAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

public class ClientMain {
	
	private static final Logger logger  = Logger.getLogger(ClientMain.class);

	public static void main(String[] args) {
		
		logger.info("Java Pad Server booting up ...");
		
		logger.info("Loading configuration ...");
		
		CyclicBarrier syncBarrier = null;
		CountDownLatch completionTriggerLatch = null;
		
		Properties props = new Properties();
		InputStream ins = ClientMain.class.getClassLoader().getResourceAsStream("client_config.properties");
		if(ins != null) {
			try {
				props.load(ins);
			} catch (IOException e) {
				logger.error("Failed to load client_config.properties", e);
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
			logger.error("Failed to find client_config.properties");
			System.exit(-1);
		}
		
		PropsPojoAdapter<ClientConfig> pojoAdpt = new PropsPojoAdapter<ClientConfig>(false);
		ClientConfig cfg = null;
		
		try {
			cfg = pojoAdpt.transformProps(props, ClientConfig.class);
			logger.info("Loaded client config = " + cfg);
		} catch (ConfigException e) {
			logger.error("Failed to parse server_config.properties", e);
			System.exit(-1);
		}
		
		logger.info("Loading test data ...");
		DataStore store = DataStore.getInstance();
		if(store.size() == 0) {
			logger.error("Failed to load data");
			System.exit(-1);
		}
		
		syncBarrier = new CyclicBarrier(cfg.getNoOfThreads());
		completionTriggerLatch = new CountDownLatch(cfg.getNoOfThreads());
		List<ClientWorker> workerList = new ArrayList<ClientWorker>();
		
		for(int i=0; i<cfg.getNoOfThreads(); i++) {
			ClientWorker worker = new ClientWorker(cfg, i + 1);
			worker.setCompletionTriggerLatch(completionTriggerLatch);;
			worker.setSyncBarrier(syncBarrier);
			workerList.add(worker);
			Thread workerThread = new Thread(worker);
			workerThread.setName("Worker-" + (i + 1));
			workerThread.start();
		}
		
		try {
			completionTriggerLatch.await();
		} catch (InterruptedException e) {
			logger.warn("Completion trigger has been set prematuarly", e);
		}
		
		long totalAcceptTime = 0L;
		long minTotalAcceptTime = Long.MAX_VALUE;
		long maxTotalAcceptTime = 0L;
		long totalProcessTime = 0L;
		long minTotalProcessTime = Long.MAX_VALUE;
		long maxTotalProcessTime = 0L;
		long maxFirstRequestTime = 0L;
		
		// Read statistics
		for(ClientWorker worker : workerList) {
			logger.info("Worker-" + worker.getWorkerId() + " Accept Time: " 
					+ worker.getAcceptTime() + " ms" + ", Process time: " + worker.getProcessTime()
					+ ", First Request Time: " + worker.getFirstRequestTime() + " ms");
			totalAcceptTime += worker.getAcceptTime();
			if(worker.getAcceptTime() < minTotalAcceptTime)
				minTotalAcceptTime = worker.getAcceptTime();
			if(worker.getAcceptTime() > maxTotalAcceptTime)
				maxTotalAcceptTime = worker.getAcceptTime();
			totalProcessTime += worker.getProcessTime();
			if(worker.getProcessTime() < minTotalProcessTime)
				minTotalProcessTime = worker.getProcessTime();
			if(worker.getProcessTime() > maxTotalProcessTime)
				maxTotalProcessTime = worker.getProcessTime();
			if(worker.getFirstRequestTime() > maxFirstRequestTime)
				maxFirstRequestTime = worker.getFirstRequestTime();
		}
		
		float avgTotalAcceptTime = (float) totalAcceptTime / (float) cfg.getNoOfThreads();
		float avgTotalProcessTime = (float) totalProcessTime / (float) cfg.getNoOfThreads();
		logger.info("Max first request time = " + maxFirstRequestTime + " ms");
		logger.info("Min Accept time: " + minTotalAcceptTime + " ms Max Accept time: " + maxTotalAcceptTime
				+ " ms Avg Accept Time: " + avgTotalAcceptTime + " ms");
		logger.info("Min Process time: " + minTotalProcessTime + " ms Max Process time: " + maxTotalProcessTime
				+ " ms Avg Process Time: " + avgTotalProcessTime + " ms");
	}

}
