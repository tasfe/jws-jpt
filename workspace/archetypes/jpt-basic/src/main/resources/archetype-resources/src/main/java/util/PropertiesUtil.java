package ${package}.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertiesUtil {

	private static final Log logger = LogFactory.getLog(PropertiesUtil.class);

	public static Properties getProperties(String name) {
		Properties props = new Properties();
		loadProperties(props, name);
		return props;
	}

	public static Properties getProperties(File file) {
		Properties props = new Properties();
		loadProperties(props, file);
		return props;
	}

	public static void loadProperties(Properties props, String name) {
		if (props != null) {
			InputStream in = null;
			try {
				in = PropertiesUtil.class.getClassLoader().getResourceAsStream(
						name);
				if (in != null) {
					props.load(in);
				}
			} catch (IOException e) {
				logger.error("load " + name + " error!");
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						logger.error("close " + name + " error!");
					}
				}
			}
		}
	}

	public static void loadProperties(Properties props, File file) {
		if (props != null) {
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				if (in != null) {
					props.load(in);
				}
			} catch (IOException e) {
				logger.error("load " + file.getName() + " error!");
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						logger.error("close " + file.getName() + " error!");
					}
				}
			}
		}
	}

}
