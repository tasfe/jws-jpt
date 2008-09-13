package ${package}.dao.ibatis.ext;

import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

import com.ibatis.common.beans.Probe;
import com.ibatis.common.beans.ProbeFactory;
import com.ibatis.sqlmap.engine.mapping.sql.dynamic.elements.ConditionalTagHandler;
import com.ibatis.sqlmap.engine.mapping.sql.dynamic.elements.SqlTag;
import com.ibatis.sqlmap.engine.mapping.sql.dynamic.elements.SqlTagContext;

public class IsPropertyAvailableTagHandler extends ConditionalTagHandler {

	private static final Probe PROBE = ProbeFactory.getProbe();

	public boolean isCondition(SqlTagContext ctx, SqlTag tag,
			Object parameterObject) {
		if (parameterObject == null) {
			return false;
		} else if (parameterObject instanceof Map) {
			String property = tag.getPropertyAttr();
			if (property.startsWith("=")) {
				return getOgnlValue(parameterObject, property);
			}
			return ((Map) parameterObject).containsKey(property);
		} else {
			String property = getResolvedProperty(ctx, tag);
			if (property.startsWith("=")) {
				return getOgnlValue(parameterObject, property);
			}

			// if this is a compound property, then we need to get the next to
			// the last
			// value from the parameter object, and then see if there is a
			// readable property
			// for the last value. This logic was added for IBATIS-281 and
			// IBATIS-293
			int lastIndex = property.lastIndexOf('.');
			if (lastIndex != -1) {
				String firstPart = property.substring(0, lastIndex);
				String lastPart = property.substring(lastIndex + 1);
				parameterObject = PROBE.getObject(parameterObject, firstPart);
				property = lastPart;
			}

			if (parameterObject instanceof Map) {
				// we do this because the PROBE always returns true for
				// properties in Maps and that's not the behavior we want here
				return ((Map) parameterObject).containsKey(property);
			} else {
				return PROBE.hasReadableProperty(parameterObject, property);
			}
		}
	}

	private boolean getOgnlValue(Object parameterObject, String property) {
		try {
			Object result = Ognl.getValue(property.substring(1),
					parameterObject);
			if (result instanceof Boolean) {
				return (Boolean) result;
			}
			return result != null;
		} catch (OgnlException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
}
