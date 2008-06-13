package ${package}.dao.ibatis.ext;

import java.sql.Connection;
import java.sql.SQLException;

import ${package}.dao.dialect.Dialect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.RequestScope;

public class JptSqlExecutor extends SqlExecutor {

	private static final Log logger = LogFactory.getLog(JptSqlExecutor.class);

	private Dialect dialect;

	private boolean enableLimit = true;

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public boolean isEnableLimit() {
		return enableLimit;
	}

	public void setEnableLimit(boolean enableLimit) {
		this.enableLimit = enableLimit;
	}

	@Override
	public void executeQuery(RequestScope request, Connection conn, String sql,
			Object[] parameters, int skipResults, int maxResults,
			RowHandlerCallback callback) throws SQLException {
		if ((skipResults != NO_SKIPPED_RESULTS || maxResults != NO_MAXIMUM_RESULTS)
				&& supportsLimit()) {
			sql = dialect.getLimitString(sql, skipResults, maxResults);
			skipResults = NO_SKIPPED_RESULTS;
			maxResults = NO_MAXIMUM_RESULTS;
		}

		try {
			super.executeQuery(request, conn, sql, parameters, skipResults,
					maxResults, callback);
			if (logger.isDebugEnabled()) {
				logger.debug(stripSql(sql));
			}
		} catch (SQLException e) {
			if (logger.isErrorEnabled()) {
				logger.error(stripSql(sql));
			}
			throw e;
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(stripSql(sql));
			}
			throw new RuntimeException(e);
		}
	}

	@Override
	public int executeUpdate(RequestScope request, Connection conn, String sql,
			Object[] parameters) throws SQLException {
		int rows = 0;
		try {
			rows = super.executeUpdate(request, conn, sql, parameters);
			if (logger.isDebugEnabled()) {
				logger.debug(stripSql(sql));
			}
		} catch (SQLException e) {
			if (logger.isErrorEnabled()) {
				logger.error(stripSql(sql));
			}
			throw e;
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(stripSql(sql));
			}
			throw new RuntimeException(e);
		}
		return rows;
	}

	public boolean supportsLimit() {
		if (enableLimit && dialect != null) {
			return dialect.supportsLimit();
		}
		return false;
	}
	
	private String stripSql(String sql) {
		sql = sql.replaceAll("\\s+", " ");
		sql = sql.replaceAll("\\s*,\\s*", ",");
		sql = sql.replaceAll("\\s*\\(\\s*", "(");
		sql = sql.replaceAll("\\s*\\)\\s*", ")");
		
		return sql;
	}

}