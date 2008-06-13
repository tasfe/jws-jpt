package ${package}.commons;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class Config {

	protected final Log logger = LogFactory.getLog(getClass());

	public boolean getBoolean(String key, boolean defaultValue) {
		String value = getConfig(key);
		if ("1".equals(value) || "true".equalsIgnoreCase(value)) {
			return true;
		}
		return defaultValue;
	}

	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	public int getInteger(String key, int defaultValue) {
		String value = getConfig(key);
		if (value != null) {
			return Integer.parseInt(value);
		}
		return defaultValue;
	}

	public String getConfig(String key, String defaultValue) {
		String value = getConfig(key);
		return (value == null) ? defaultValue : value;
	}

	public abstract String getConfig(String key);

}