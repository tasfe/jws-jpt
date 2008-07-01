package ${package}.dao.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ClassUtils;

import ${package}.util.ReflectUtil;

public final class iBatisDaoUtils {

	protected static final Log logger = LogFactory.getLog(iBatisDaoUtils.class);

	private static final String PRIMARY_KEY = "id";

	private static final int RAND_OBJECTS_MAX_NUM = 100;

	private iBatisDaoUtils() {
	}

	public static String getSelectQuery(Class clazz) {
		return "get" + ClassUtils.getShortName(clazz) + "s";
	}

	public static String getRandQuery(Class clazz) {
		return "getRand" + ClassUtils.getShortName(clazz) + "s";
	}

	public static String getFindQuery(Class clazz) {
		return "get" + ClassUtils.getShortName(clazz);
	}

	public static String getInsertQuery(Class clazz) {
		return "add" + ClassUtils.getShortName(clazz);
	}

	public static String getUpdateQuery(Class clazz) {
		return "update" + ClassUtils.getShortName(clazz);
	}

	public static String getDeleteQuery(Class clazz) {
		return "delete" + ClassUtils.getShortName(clazz);
	}

	public static String getFindQuery(String name) {
		return "get" + StringUtils.capitalize(name);
	}

	public static String getInsertQuery(String name) {
		return "add" + StringUtils.capitalize(name);
	}

	public static String getUpdateQuery(String name) {
		return "update" + StringUtils.capitalize(name);
	}

	public static String[] getNextQuery(String query, int current, String prefix) {
		if (query == null) {
			return null;
		}
		String join = ".";
		if (prefix != null) {
			join += prefix + ".";
		}
		current++;
		return new String[] { query + join + current + ".s",
				query + join + current + ".u", query + join + current + ".i" };
	}

	public static String getDeleteQuery(String name) {
		return "delete" + StringUtils.capitalize(name);
	}

	public static Object getPrimaryKey(Object o) {
		return ReflectUtil.getFieldValue(o, PRIMARY_KEY, Long.class);
	}

	public static void setPrimaryKey(Object o, Object primaryKey) {
		ReflectUtil.setFieldValue(o, PRIMARY_KEY, Long.class, primaryKey);
	}

	public static Map<String, Object> buildRands(Object parameter, int limit) {
		Set<Double> rands = new HashSet<Double>(limit);
		for (int i = 0; i < RAND_OBJECTS_MAX_NUM && rands.size() < limit; i++) {
			rands.add(Math.random());
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("rands", new ArrayList<Double>(rands));
		parameters.put("param", parameter);
		parameters.put("limit", limit);
		return parameters;
	}
}
