package in.co.sh00nya.test;

import in.co.sh00nya.cmn.ConfigException;
import in.co.sh00nya.cmn.PropsPojoAdapter;

public class TestPojo {
	
	private int someIntVar;
	
	private float someFloatVar;
	
	private double someDoubleVar;
	
	private char someCharVar;
	
	private byte someByteVar;
	
	private short someShortVar;
	
	private String someStringVar;
	
	private long someLongVar;

	public int getSomeIntVar() {
		return someIntVar;
	}

	public void setSomeIntVar(int someIntVar) {
		this.someIntVar = someIntVar;
	}

	public float getSomeFloatVar() {
		return someFloatVar;
	}

	public void setSomeFloatVar(float someFloatVar) {
		this.someFloatVar = someFloatVar;
	}

	public double getSomeDoubleVar() {
		return someDoubleVar;
	}

	public void setSomeDoubleVar(double someDoubleVar) {
		this.someDoubleVar = someDoubleVar;
	}

	public char getSomeCharVar() {
		return someCharVar;
	}

	public void setSomeCharVar(char someCharVar) {
		this.someCharVar = someCharVar;
	}

	public byte getSomeByteVar() {
		return someByteVar;
	}

	public void setSomeByteVar(byte someByteVar) {
		this.someByteVar = someByteVar;
	}

	public short getSomeShortVar() {
		return someShortVar;
	}

	public void setSomeShortVar(short someShortVar) {
		this.someShortVar = someShortVar;
	}

	public String getSomeStringVar() {
		return someStringVar;
	}

	public void setSomeStringVar(String someStringVar) {
		this.someStringVar = someStringVar;
	}

	public long getSomeLongVar() {
		return someLongVar;
	}

	public void setSomeLongVar(long someLongVar) {
		this.someLongVar = someLongVar;
	}
	
	public static void main(String[] args) {
		PropsPojoAdapter<TestPojo> pojoAdpt = new PropsPojoAdapter<TestPojo>();
		try {
			TestPojo pojoObj = pojoAdpt.transformProps(null, TestPojo.class);
		} catch (ConfigException e) {
			e.printStackTrace();
		}
	}

}
