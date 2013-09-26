package in.co.sh00nya.cmn;

import java.util.Properties;

public class PropsPojoAdapter<T> {
	
	private boolean strict = false;

	public PropsPojoAdapter() {
	}
	
	public PropsPojoAdapter(boolean strict) {
		this.strict = strict;
	}
	
	public T transformProps(Properties props, Class<T> pojoClass) throws ConfigException {
		T pojoObj = null;
		try {
			pojoObj = pojoClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pojoObj;
	}
	
}
