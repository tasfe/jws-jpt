package ${package}.commons;

import java.io.File;
import java.util.Properties;

import org.springframework.util.ResourceUtils;

import ${package}.util.PropertiesUtil;

public class ReloadableConfig extends Config {

	protected String name;

	protected File file;

	protected Properties props;

	protected long lastModified;

	public ReloadableConfig(String name) {
		this.name = name;
		init();
	}

	@Override
	public String getConfig(String key) {
		load();
		return props.getProperty(key);
	}

	protected void init() {
		try {
			file = ResourceUtils.getFile(name);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage(), e.getCause());
			}
		}
		lastModified = 0;
		props = new Properties();
	}

	protected void load() {
		if (file != null && file.exists() && file.lastModified() > lastModified) {
			PropertiesUtil.loadProperties(props, file);
			lastModified = file.lastModified();
		}
	}

}
