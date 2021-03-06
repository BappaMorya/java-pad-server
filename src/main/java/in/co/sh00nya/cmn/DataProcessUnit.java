package in.co.sh00nya.cmn;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import org.apache.log4j.Logger;

/**
 * Processes incoming data using special protocol as follows:
 * <Text Line>$CHECK$<Text Line>$CHECK$ %STOP%$CHECK$ ....
 * At each $CHECK$, we send back count till that point as <count>$ and reset counter
 * %STOP%$CHECK$ marks end of data
 * 
 * @author Siddharth_Godbole
 *
 */
public class DataProcessUnit {
	
	private static final Logger logger = Logger.getLogger(DataProcessUnit.class);
	
	public static final String CHECK_TOKEN = "$CHECK$";
	
	public static final String STOP_TOKEN = "%STOP%";

	/**
	 * See class comments to understand protocol
	 * @param ins
	 * @param ous
	 * @throws IOException
	 */
	public static void processData(InputStream ins, OutputStream ous)
			throws IOException {
		logger.info("Processing data from client ...");
		InputStreamReader inReader = new InputStreamReader(ins);
		while (true) {
			String partialData = readUntil(inReader, CHECK_TOKEN);
			if (partialData.endsWith(STOP_TOKEN)) {
				logger.debug("Received signal to stop processing ...");
				ins.close();
				ous.close();
				break;
			} else {
				int wordCount = WordCountUtil.countWord(partialData);
				logger.debug("Sending partial count (" + wordCount + ") back");
				ous.write(makeCheckResponse(wordCount).getBytes());
				ous.flush();
			}
		}
	}
	
	/**
	 * Reads from input stream character by character till it reads token string
	 * @param ins
	 * @param token
	 * @throws IOException
	 */
	public static String readUntil(Reader reader, String token) throws IOException {
		StringBuffer buf = new StringBuffer();
		while (true) {
			buf.append((char) reader.read());
			if(buf.toString().endsWith(token)) {
				String tempData = buf.toString();
				return tempData.substring(0, tempData.indexOf(token));
			}
		}
	}
	
	/**
	 * Returns unique word count word, returns -1 if its a stop signal
	 * @param data
	 * @return
	 */
	public static int processData(String data) {
		String tempData = data.substring(0, data.indexOf(CHECK_TOKEN)).trim();
		if(tempData.length() > 0) {
			if(tempData.equals(STOP_TOKEN))
				return -1;
			else
				return WordCountUtil.countWord(tempData);
		}
		return -1;
	}
	
	public static String getSubData(String data) {
		return data.substring(data.indexOf(CHECK_TOKEN) + CHECK_TOKEN.length()).trim();
	}
	
	public static String makeCheckResponse(int count) {
		StringBuilder builder = new StringBuilder();
		builder.append(count).append('$');
		return builder.toString();
	}

}
