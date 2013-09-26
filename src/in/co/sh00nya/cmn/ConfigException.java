package in.co.sh00nya.cmn;

public class ConfigException extends Exception {
	
	private StringBuffer buffer = new StringBuffer();
	
	{
		buffer.append("ConfigException: Config failed for -> ");
	}
	
	public void addCfgAttr(String attrName, String value) {
		buffer.append("[attr: " + attrName + ", val: " + value + "] ");
	}
	
	public void addCfgAttr(String attrName) {
		buffer.append("[attr: " + attrName + "] ");
	}

	@Override
	public String toString() {
		return buffer.toString();
	}

	@Override
	public String getMessage() {
		return buffer.toString();
	}

}
