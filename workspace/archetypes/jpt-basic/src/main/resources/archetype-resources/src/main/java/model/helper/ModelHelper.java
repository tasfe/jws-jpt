package ${package}.model.helper;

import ognl.Ognl;
import ognl.OgnlException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ClassUtils;

import ${package}.Constants;
import ${package}.model.PublicObject;

public class ModelHelper {

	private static final Log logger = LogFactory.getLog(ModelHelper.class);

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
		String dependencies = Constants.CONFIG.getConfig("model.dependencies."
				+ name);
		if (dependencies == null) {
			return null;
		}
		return dependencies.split(",");
	}

}
