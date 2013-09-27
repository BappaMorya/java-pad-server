package in.co.sh00nya.cmn;

public class TextUtils {
	
	public static String toCamelCase(String origString) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(Character.toUpperCase(origString.charAt(0)));
		buffer.append(origString.substring(1));
		return buffer.toString();
	}

}
