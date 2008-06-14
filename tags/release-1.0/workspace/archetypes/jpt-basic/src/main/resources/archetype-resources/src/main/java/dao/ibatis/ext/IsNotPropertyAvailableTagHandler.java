package ${package}.dao.ibatis.ext;

import com.ibatis.sqlmap.engine.mapping.sql.dynamic.elements.SqlTag;
import com.ibatis.sqlmap.engine.mapping.sql.dynamic.elements.SqlTagContext;

public class IsNotPropertyAvailableTagHandler extends
		IsPropertyAvailableTagHandler {

	public boolean isCondition(SqlTagContext ctx, SqlTag tag,
			Object parameterObject) {
		return !super.isCondition(ctx, tag, parameterObject);
	}

}
