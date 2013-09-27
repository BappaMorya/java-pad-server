package in.co.sh00nya.cmn;

public class Employee {

	private int age;

	private String firstName;

	private String lastName;

	private float salary;

	private boolean flag;

	private long value;

	private double doubleData;

	private byte byteData;

	private short shortData;

	private char charData;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
	
	// should not take this on in
	public void setValue(long value, long abc) {
		
	}

	public double getDoubleData() {
		return doubleData;
	}

	public void setDoubleData(double doubleData) {
		this.doubleData = doubleData;
	}

	public byte getByteData() {
		return byteData;
	}

	public void setByteData(byte byteData) {
		this.byteData = byteData;
	}

	public short getShortData() {
		return shortData;
	}

	public void setShortData(short shortData) {
		this.shortData = shortData;
	}

	public char getCharData() {
		return charData;
	}

	public void setCharData(char charData) {
		this.charData = charData;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

}
