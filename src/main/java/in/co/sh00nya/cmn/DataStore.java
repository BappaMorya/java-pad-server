package in.co.sh00nya.cmn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class DataStore {
	
	private static final Logger logger = Logger.getLogger(DataStore.class);
	
	private List<String> dataLines = new LinkedList<String>();
	
	private static DataStore instance = null;
	
	private DataStore() {
		
	}
	
	private void loadData() {
		BufferedReader bins = null;
		try {
			InputStream ins = DataStore.class.getClassLoader().getResourceAsStream("lines.dat");
			if(ins == null)
				return;
			 bins = new BufferedReader(new InputStreamReader(ins));
			 String line = null;
			 while((line = bins.readLine()) != null)
				 dataLines.add(line);
			 
		} catch (FileNotFoundException e) {
			logger.error("Failed to find data file lines.dat", e);
			return;
		} catch (IOException e) {
			logger.error("Failed to read data file lines.dat", e);
			return;
		} finally {
			try {
				if(bins != null)
					bins.close();
			} catch (IOException e) {
				// ignore failure here
			}
		}
	}
	
	public static DataStore getInstance() {
		synchronized (DataStore.class) {
			if(instance == null) {
				instance = new DataStore();
				instance.loadData();
			}
			return instance;
		}
	}
	
	public String getDataLine(int index) {
		if(index >= dataLines.size())
			return null;
		else
			return dataLines.get(index);
	}
	
	public int size() {
		return dataLines.size();
	}

}
