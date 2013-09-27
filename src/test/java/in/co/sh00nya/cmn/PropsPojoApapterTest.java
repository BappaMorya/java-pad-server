package in.co.sh00nya.cmn;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PropsPojoApapterTest {
	
	private PropsPojoAdapter<Employee> strictPojoAdpt = null;
	private PropsPojoAdapter<Employee> nonStrictPojoAdpt = null;
	
	@Before
	public void before() {
		strictPojoAdpt = new PropsPojoAdapter<Employee>(true);
		nonStrictPojoAdpt= new PropsPojoAdapter<Employee>(false);
	}
	
	@Test(expected = ConfigException.class)
	public void testParameterizedConstructor() throws ConfigException {
		PropsPojoAdapter<StrictClass> strictPojoAdpt = new PropsPojoAdapter<StrictClass>();
		Properties props = new Properties();
		props.put("value1", "1000000000");
		strictPojoAdpt.transformProps(props, StrictClass.class);
	}
	
	@Test(expected = ConfigException.class)
	public void testStrictMissingProperty() throws ConfigException {
		Properties props = new Properties();
		props.put("value1", "1000000000");
		strictPojoAdpt.transformProps(props, Employee.class);
	}
	
	@Test
	public void testNonStrictMissingProperty() {
		Properties props = new Properties();
		props.put("value1", "1000000000");
		boolean expected = false;
		try {
			nonStrictPojoAdpt.transformProps(props, Employee.class);
		} catch (ConfigException e) {
			expected = true;
		}
		org.junit.Assert.assertEquals(expected, false);
	}
	
	@Test(expected = ConfigException.class)
	public void testStrictDatatypeMispatch() throws ConfigException {
		Properties props = new Properties();
		props.put("age", "hello");
		strictPojoAdpt.transformProps(props, Employee.class);
	}
	
	@Test
	public void testNonStrictDatatypeMispatch() {
		Properties props = new Properties();
		props.put("age", "hello");
		boolean expected = false;
		try {
			nonStrictPojoAdpt.transformProps(props, Employee.class);
		} catch (ConfigException e) {
			expected = true;
		}
		org.junit.Assert.assertEquals(expected, false);
	}
	
	@Test
	public void testIntDataType() {
		Properties props = new Properties();
		props.put("age", "100");
		Employee emp = null;
		try {
			emp = strictPojoAdpt.transformProps(props, Employee.class);
		} catch (ConfigException e) {
		}
		org.junit.Assert.assertEquals(emp.getAge(), 100);
	}
	
	@Test
	public void testStringDataType() {
		Properties props = new Properties();
		props.put("firstName", "SidGod");
		Employee emp = null;
		try {
			emp = strictPojoAdpt.transformProps(props, Employee.class);
		} catch (ConfigException e) {
		}
		org.junit.Assert.assertEquals(emp.getFirstName(), "SidGod");
	}
	
	@Test
	public void testFloatDataType() {
		Properties props = new Properties();
		props.put("salary", "100.00");
		Employee emp = null;
		try {
			emp = strictPojoAdpt.transformProps(props, Employee.class);
		} catch (ConfigException e) {
		}
		org.junit.Assert.assertEquals(emp.getSalary(), 100.00, 0.10);
	}
	
	@Test
	public void testBooleanDataType() {
		Properties props = new Properties();
		props.put("flag", "true");
		Employee emp = null;
		try {
			emp = strictPojoAdpt.transformProps(props, Employee.class);
		} catch (ConfigException e) {
		}
		org.junit.Assert.assertEquals(emp.isFlag(), true);
	}
	
	@Test
	public void testLongDataType() {
		Properties props = new Properties();
		props.put("value", "1000000000");
		Employee obj = null;
		try {
			obj = strictPojoAdpt.transformProps(props, Employee.class);
		} catch (ConfigException e) {
			e.printStackTrace();
		}
		org.junit.Assert.assertEquals(obj.getValue(), 1000000000);
	}
	
	@Test
	public void testDoubleDataType() {
		Properties props = new Properties();
		props.put("doubleData", "10000.123");
		Employee emp = null;
		try {
			emp = strictPojoAdpt.transformProps(props, Employee.class);
		} catch (ConfigException e) {
		}
		org.junit.Assert.assertEquals(emp.getDoubleData(), 10000.123, 0.10);
	}
	
	@Test
	public void testByteDataType() {
		Properties props = new Properties();
		props.put("age", "100");
		Employee emp = null;
		try {
			emp = strictPojoAdpt.transformProps(props, Employee.class);
		} catch (ConfigException e) {
		}
		org.junit.Assert.assertEquals(emp.getAge(), 100);
	}
	
	@Test
	public void testShortDataType() {
		Properties props = new Properties();
		props.put("shortData", "100");
		Employee emp = null;
		try {
			emp = strictPojoAdpt.transformProps(props, Employee.class);
		} catch (ConfigException e) {
		}
		org.junit.Assert.assertEquals(emp.getShortData(), Byte.parseByte("100"));
	}
	
	@Test
	public void testCharDataType() {
		Properties props = new Properties();
		props.put("charData", "c");
		Employee emp = null;
		try {
			emp = strictPojoAdpt.transformProps(props, Employee.class);
		} catch (ConfigException e) {
		}
		org.junit.Assert.assertEquals(emp.getCharData(), 'c');
	}

}
