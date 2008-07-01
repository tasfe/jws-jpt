package ${package}.model.helper;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import ognl.Ognl;
import ognl.OgnlException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import ${package}.Constants;
import ${package}.model.PublicObject;
import ${package}.util.PropertiesUtil;

public class ModelHelper {

	private static final Log logger = LogFactory.getLog(ModelHelper.class);

	private static Map<String, String[]> dependencies;

	private ModelHelper() {
	}

	public static Object getModel(String name, String postfix) {
		String classnameBase = "${package}.model."
				+ StringUtils.capitalize(name);
		String classname = classnameBase;
		if (postfix != null) {
			classname = classname + postfix;
		}
		try {
			return ClassUtils.forName(classname).newInstance();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("load " + classname + " fail.");
			}
			if (postfix != null) {
				try {
					return ClassUtils.forName(classnameBase).newInstance();
				} catch (Exception ignored) {
					if (logger.isDebugEnabled()) {
						logger.debug("load " + classnameBase + " fail.");
					}
				}
			}
			return new PublicObject();
		}
	}

	public static void setLoginUser(Object model, Object user) {
		try {
			Ognl.setValue("ctx." + Constants.LOGIN_USER, model, user);
		} catch (OgnlException ignored) {
		}
	}

	public static String[] getModelDependency(String name) {
		init();
		return dependencies.get(name);
	}

	private static void init() {
		if (dependencies == null) {
			dependencies = new HashMap<String, String[]>();
			try {
				Properties props = PropertiesUtil
						.getProperties(ResourceUtils
								.getFile("classpath:jpt.model.dependencies.properties"));
				for (Enumeration e = props.propertyNames(); e.hasMoreElements();) {
					String name = (String) e.nextElement();
					String value = props.getProperty(name);
					if (value != null) {
						dependencies.put(name, value.split(","));
					}
				}

			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
		}
	}

}
