package in.co.sh00nya.server.nio;

import in.co.sh00nya.cmn.DataProcessUnit;
import in.co.sh00nya.cmn.ServerException;
import in.co.sh00nya.server.IServer;
import in.co.sh00nya.server.ServerConfig;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

public class NIOSocketServer implements IServer {
	
	private static final Logger logger = Logger.getLogger(NIOSocketServer.class);
	
	private volatile boolean isRunning = true;
	
	private ServerConfig serverConfig = null;
	
	private ServerSocket serverSock = null;
	
	private Selector sel = null;
	
	private ByteBuffer dataBuffer = ByteBuffer.allocate( 1024 );
	
	private Map<String, StringBuilder> dataCache = new HashMap<String, StringBuilder>();
	
	@Override
	public void setConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	@Override
	public void bindServer() throws ServerException {
		logger.info("Creating server at " + serverConfig);
		try {
			ServerSocketChannel serverSockChan = ServerSocketChannel.open();
			serverSockChan.configureBlocking(false);
			serverSock = serverSockChan.socket();
			serverSock.bind(new InetSocketAddress(serverConfig.getBindIpAddress(), serverConfig.getBindPort()));
			sel = Selector.open();
			SelectionKey selKey = serverSockChan.register(sel, SelectionKey.OP_ACCEPT);
			logger.debug("Registred server socket for Accept events with key =  " + selKey);
		} catch (IOException e) {
			logger.error("Failed to open server socket", e);
			throw new ServerException("Failed to open server socket", e);
		}
	}

	@Override
	public void startServer() throws ServerException {
		logger.info("Starting to accept client connections ...");
		while(isRunning) {
			try {
				int numSel = sel.select();
				logger.debug("Received " + numSel + " available events");
				
				Iterator<SelectionKey> itr = sel.selectedKeys().iterator();
				
				while(itr.hasNext()) {
					SelectionKey selKey = itr.next();
					logger.debug("Working on selection key = " + selKey);
					
					if((selKey.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
						// Accept new connection
						ServerSocketChannel ssc = (ServerSocketChannel) selKey.channel();
						SocketChannel sockChan = ssc.accept();
						sockChan.configureBlocking(false);
						logger.debug("Accepted connection from " + sockChan.socket());
						
						// register selector with new channel for read operation
						SelectionKey readSockKey = sockChan.register(sel, SelectionKey.OP_READ, sockChan.getRemoteAddress().toString());
						logger.debug("Selection key for read select = " + readSockKey);
						dataCache.put(sockChan.getRemoteAddress().toString(), new StringBuilder());
						itr.remove();
					} else if ((selKey.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
						SocketChannel sockChan = (SocketChannel) selKey.channel();
						dataBuffer.clear();
						int bytesRead = 0;
						while(true) {
							bytesRead = sockChan.read(dataBuffer);
							logger.debug("Read bytes = " + bytesRead);
							
							if(bytesRead <= 0)
								break;
							
							dataBuffer.flip();
							
							byte[] data = new byte[dataBuffer.limit()];
							
							dataBuffer.get(data);
							
							StringBuilder dataBuilder = dataCache.get(sockChan.getRemoteAddress().toString());
							dataBuilder.append(new String(data));
							logger.debug("Received string = " + String.valueOf(data));
							String wholeString = dataBuilder.toString();
							logger.debug("Whole string = " + wholeString);
							if(wholeString.endsWith(DataProcessUnit.CHECK_TOKEN)) {
								dataBuffer.clear();
								int count = DataProcessUnit.processData(wholeString);
								if(count == -1) {
									dataCache.remove(sockChan.getRemoteAddress().toString());
									sockChan.close();
									selKey.cancel();
									break;
								} else {
									dataBuilder.setLength(0);
									dataBuilder.append(DataProcessUnit.getSubData(wholeString));
									dataBuffer.put(DataProcessUnit.makeCheckResponse(count).getBytes());
									dataBuffer.flip();
									sockChan.write(dataBuffer);
								}
							}
						}
						
						itr.remove();
					}
				}
				
			} catch (IOException e) {
				logger.error("Failed to get available select events", e);
				throw new ServerException("Failed to get available select events", e);
			}
		}
	}

	@Override
	public void stopServer() throws ServerException {
		isRunning = false;
		if(serverSock != null) {
			logger.info("Shutting down server ...");
			try {
				serverSock.close();
			} catch (IOException e) {
				logger.error("Failed to shut down server ...", e);
			}
		}
	}

}
