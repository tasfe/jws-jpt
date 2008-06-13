package ${package}.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReflectUtil {

	private static final Log logger = LogFactory.getLog(ReflectUtil.class);

	@SuppressWarnings("unchecked")
	public static void setFieldValue(Object target, String fname, Class ftype,
			Object fvalue) {
		if (target == null
				|| fname == null
				|| "".equals(fname)
				|| (fvalue != null && !ftype
						.isAssignableFrom(fvalue.getClass()))) {
			return;
		}

		Class clazz = target.getClass();
		try {
			Method method = clazz.getDeclaredMethod("set"
					+ Character.toUpperCase(fname.charAt(0))
					+ fname.substring(1), ftype);
			if (!Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
			}
			method.invoke(target, fvalue);

		} catch (Exception me) {
			if (logger.isDebugEnabled()) {
				logger.debug(me);
			}
			try {
				Field field = clazz.getDeclaredField(fname);
				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
				}
				field.set(target, fvalue);
			} catch (Exception fe) {
				if (logger.isDebugEnabled()) {
					logger.debug(fe);
				}
				if (target instanceof Map) {
					((Map) target).put(fname, fvalue);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static Object getFieldValue(Object target, String fname, Class ftype) {
		if (target == null || fname == null || "".equals(fname)) {
			return null;
		}
		Class clazz = target.getClass();
		try {
			Method method = clazz.getDeclaredMethod("get"
					+ Character.toUpperCase(fname.charAt(0))
					+ fname.substring(1), ftype);
			if (!Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
			}
			return method.invoke(target);

		} catch (Exception me) {
			if (logger.isDebugEnabled()) {
				logger.debug(me);
			}
			try {
				Field field = clazz.getDeclaredField(fname);
				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
				}
				return field.get(target);
			} catch (Exception fe) {
				if (logger.isDebugEnabled()) {
					logger.debug(fe);
				}
				if (target instanceof Map) {
					return ((Map) target).get(fname);
				}
			}
		}
		return null;
	}

	public static Object getStaticFieldValue(Class clazz, String fname) {
		try {
			Field field = clazz.getDeclaredField(fname);
			if (!Modifier.isPublic(field.getModifiers())) {
				field.setAccessible(true);
			}
			return field.get(null);
		} catch (Exception fe) {
			if (logger.isDebugEnabled()) {
				logger.debug(fe);
			}
		}
		return null;
	}
}
