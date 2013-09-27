package in.co.sh00nya.cmn;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Transforms properties file into POJO attribute values
 * Generic utility that can populate POJO object with property values
 * Property names in properties should be same as attribute names in POJO class
 * 
 * @author Siddharth_Godbole
 *
 * @param <T>
 */
public class PropsPojoAdapter <T> {
	
	private static final Logger logger = Logger.getLogger(PropsPojoAdapter.class);
	
	private boolean strict = false;

	public PropsPojoAdapter() {
	}
	
	public PropsPojoAdapter(boolean strict) {
		this.strict = strict;
	}
	
	/**
	 * Transforms property values into values of POJO attributes
	 * @param props Property values
	 * @param pojoClass POJO class whose object will be populated with property values
	 * @return
	 * @throws ConfigException
	 */
	public T transformProps(Properties props, Class<T> pojoClass) throws ConfigException {
		
		if(props == null || props.size() == 0) {
			throw new ConfigException("Stopping messing with me you moron!");
		}
		
		logger.debug("Transforming props = " + props 
				+ " into " + pojoClass.getPackage() + "." + pojoClass.getName());
		T pojoObj = null;
		Map<String, Method> pojoProspectMap = new HashMap<String, Method>();
		
		// Check if we can instantiate POJO object 
		try {
			pojoObj = pojoClass.newInstance();
		} catch (Exception e) {
			throw new ConfigException("Failed to create POJO object", e);
		}
		
		// Propect POJO class for supported setter methods
		for(Method m : pojoClass.getMethods()) {
			if(m.getName().startsWith("set") && 
					m.getParameterTypes().length == 1) {
				pojoProspectMap.put(m.getName(), m);
			}
		}
		
		if(pojoProspectMap.size() == 0) {
			throw new ConfigException("POJO has no public setter methods");
		}
		
		// Go through each property, see if we have setter for each property in POJO
		for(Map.Entry<Object, Object> entry : props.entrySet()) {
			String propName = (String) entry.getKey();
			String propVal = (String) entry.getValue();
			
			if(propVal == null)
				continue;
			
			String setterMethodName = "set" + TextUtils.toCamelCase(propName);
			if(!pojoProspectMap.containsKey(setterMethodName)) {
				if(strict)
					throw new ConfigException("Failed to find public setter method " 
							+ setterMethodName + " for property " + propName);
				else
					continue;
			}
			
			Method setterMethod = pojoProspectMap.get(setterMethodName);
			String dataType = setterMethod.getParameterTypes()[0].getName();
			Object setterVal = null;
			try {
				setterVal = getObjVal(propVal, dataType);
			} catch (Exception cfgEx) {
				if(!strict)
					logger.warn("Failed to parse prop value " + propVal 
							+ " into data type " + dataType);
				else
					throw new ConfigException("Failed to parse prop value " + propVal 
							+ " into data type " + dataType, cfgEx);
			}
			if(setterVal != null) {
				try {
					setterMethod.invoke(pojoObj, setterVal);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					if(!strict)
						logger.warn("Failed to set prop value " + propVal 
								+ " for property " + propName);
					else
						throw new ConfigException("Failed to set prop value " + propVal 
								+ " for property " + propName, e);
				}
			}
		}
		
		return pojoObj;
	}
	
	/**
	 * Parse property value from string into matching data type
	 * @param propVal Property value
	 * @param dataType Data type extected in POJO
	 * @return
	 * @throws ConfigException
	 */
	private Object getObjVal(String propVal, String dataType) throws ConfigException {
		logger.debug("Parsing propVal = " + propVal + " for " + dataType);
		if("long".equals(dataType)) {
			return Long.parseLong(propVal);
		} else if("boolean".equals(dataType)) {
			return Boolean.parseBoolean(propVal);
		} else if("byte".equals(dataType)) {
			return Byte.parseByte(propVal);
		} else if("short".equals(dataType)) {
			return Short.parseShort(propVal);
		} else if("char".equals(dataType)) {
			return propVal.toCharArray()[0];
		} else if("int".equals(dataType)) {
			return Integer.parseInt(propVal);
		} else if("java.lang.String".equals(dataType)) {
			return propVal;
		} else if("float".equals(dataType)) {
			return Float.parseFloat(propVal);
		} else if("double".equals(dataType)) {
			return Double.parseDouble(propVal);
		} else
			throw new ConfigException("Cannot parse complex data type " + dataType);
	}
	
}
