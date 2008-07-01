package ${package}.dao.ibatis.ext;

import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.mapping.sql.dynamic.elements.SqlTagHandlerFactory;
import ${package}.util.ReflectUtil;

public class SqlMapClientFactoryBean extends
		org.springframework.orm.ibatis.SqlMapClientFactoryBean {
	private JptSqlExecutor sqlExecutor;

	public void setSqlExecutor(JptSqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}

	public void setEnableLimit(boolean enableLimit) {
		if (sqlExecutor instanceof JptSqlExecutor) {
			((JptSqlExecutor) sqlExecutor).setEnableLimit(enableLimit);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		Map HANDLER_MAP = (Map) ReflectUtil.getStaticFieldValue(
				SqlTagHandlerFactory.class, "HANDLER_MAP");
		HANDLER_MAP.put("isPropertyAvailable",
				new IsPropertyAvailableTagHandler());
		HANDLER_MAP.put("isNotPropertyAvailable",
				new IsNotPropertyAvailableTagHandler());
		super.afterPropertiesSet();
		if (sqlExecutor != null) {
			SqlMapClient sqlMapClient = (SqlMapClient) getObject();
			if (sqlMapClient instanceof ExtendedSqlMapClient) {
				ReflectUtil.setFieldValue(((ExtendedSqlMapClient) sqlMapClient)
						.getDelegate(), "sqlExecutor", SqlExecutor.class,
						sqlExecutor);

			}
		}
	}

}