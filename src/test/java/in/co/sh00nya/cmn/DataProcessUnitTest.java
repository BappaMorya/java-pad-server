package in.co.sh00nya.cmn;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DataProcessUnitTest {
	
	@Test
	public void readUntilTest() {
		String data = "asda sd ad6a8s &&** DATA_START#ashdkasd";
		InputStream ins = new ByteArrayInputStream(data.getBytes());
		InputStreamReader reader = new InputStreamReader(ins);
		char ch = ' ';
		try {
			DataProcessUnit.readUntil(reader, "DATA_START");
			ch = (char) reader.read();
		} catch (IOException e) {
		}
		Assert.assertEquals(ch, '#');
	}
	
	@Test
	public void readUntilReturnTest() {
		String data = "asda sd ad6a8s &&** DATA_START#ashdkasd";
		InputStream ins = new ByteArrayInputStream(data.getBytes());
		InputStreamReader reader = new InputStreamReader(ins);
		String ret = null;
		try {
			ret = DataProcessUnit.readUntil(reader, "DATA_START");
		} catch (IOException e) {
		}
		Assert.assertEquals(ret, "asda sd ad6a8s &&** ");
	}
	
	@Test
	public void testStringUtil() {
		String data = "this is a small line.$CHECK$";
		Assert.assertEquals(5, DataProcessUnit.processData(data));
	}
	
	@Test
	public void testStringUtilWithStop() {
		String data = "%STOP%$CHECK$";
		Assert.assertEquals(-1, DataProcessUnit.processData(data));
	}

}
