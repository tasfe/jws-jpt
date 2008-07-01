package ${package}.dao.ibatis.ext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.common.jdbc.exception.NestedSQLException;
import com.ibatis.sqlmap.client.event.RowHandler;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.result.AutoResultMap;
import com.ibatis.sqlmap.engine.mapping.result.BasicResultMap;
import com.ibatis.sqlmap.engine.mapping.result.ResultMap;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.statement.CachingStatement;
import com.ibatis.sqlmap.engine.mapping.statement.ExecuteListener;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.mapping.statement.SelectStatement;
import com.ibatis.sqlmap.engine.scope.ErrorContext;
import com.ibatis.sqlmap.engine.scope.RequestScope;
import ${package}.util.ReflectUtil;

public class CountStatementUtil {

	public static MappedStatement createCountStatement(
			MappedStatement selectStatement) {
		if (selectStatement instanceof CachingStatement) {
			return new CountStatement((SelectStatement) ReflectUtil
					.getFieldValue(selectStatement, "statement",
							SelectStatement.class));
		}
		return new CountStatement((SelectStatement) selectStatement);
	}

	public static String getCountStatementId(String selectStatementId) {
		return "__" + selectStatementId + "Count__";
	}

}

class CountStatement extends SelectStatement {

	public CountStatement(SelectStatement selectStatement) {
		super();
		setId(CountStatementUtil.getCountStatementId(selectStatement.getId()));
		setResultSetType(selectStatement.getResultSetType());
		setFetchSize(1);
		setParameterMap(selectStatement.getParameterMap());
		setParameterClass(selectStatement.getParameterClass());
		setSql(selectStatement.getSql());
		setResource(selectStatement.getResource());
		setSqlMapClient(selectStatement.getSqlMapClient());
		setTimeout(selectStatement.getTimeout());
		List executeListeners = (List) ReflectUtil.getFieldValue(
				selectStatement, "executeListeners", List.class);
		if (executeListeners != null) {
			for (Object listener : executeListeners) {
				addExecuteListener((ExecuteListener) listener);
			}
		}
		BasicResultMap resultMap = new AutoResultMap(
				((ExtendedSqlMapClient) getSqlMapClient()).getDelegate(), false);
		resultMap.setId(getId() + "-AutoResultMap");
		resultMap.setResultClass(Long.class);
		resultMap.setResource(getResource());
		setResultMap(resultMap);

	}

	@Override
	protected void executeQueryWithCallback(RequestScope request,
			Connection conn, Object parameterObject, Object resultObject,
			RowHandler rowHandler, int skipResults, int maxResults)
			throws SQLException {
		ErrorContext errorContext = request.getErrorContext();
		errorContext
				.setActivity("preparing the mapped statement for execution");
		errorContext.setObjectId(this.getId());
		errorContext.setResource(this.getResource());

		try {
			parameterObject = validateParameter(parameterObject);

			Sql sql = getSql();

			errorContext.setMoreInfo("Check the parameter map.");
			ParameterMap parameterMap = sql.getParameterMap(request,
					parameterObject);

			errorContext.setMoreInfo("Check the result map.");
			ResultMap resultMap = getResultMap(request, parameterObject, sql);

			request.setResultMap(resultMap);
			request.setParameterMap(parameterMap);

			errorContext.setMoreInfo("Check the parameter map.");
			Object[] parameters = parameterMap.getParameterObjectValues(
					request, parameterObject);

			errorContext.setMoreInfo("Check the SQL statement.");
			String sqlString = getSqlString(request, parameterObject, sql);

			errorContext.setActivity("executing mapped statement");
			errorContext
					.setMoreInfo("Check the SQL statement or the result map.");
			RowHandlerCallback callback = new RowHandlerCallback(resultMap,
					resultObject, rowHandler);
			sqlExecuteQuery(request, conn, sqlString, parameters, skipResults,
					maxResults, callback);

			errorContext.setMoreInfo("Check the output parameters.");
			if (parameterObject != null) {
				postProcessParameterObject(request, parameterObject, parameters);
			}

			errorContext.reset();
			sql.cleanup(request);
			notifyListeners();
		} catch (SQLException e) {
			errorContext.setCause(e);
			throw new NestedSQLException(errorContext.toString(), e
					.getSQLState(), e.getErrorCode(), e);
		} catch (Exception e) {
			errorContext.setCause(e);
			throw new NestedSQLException(errorContext.toString(), e);
		}
	}

	private String getSqlString(RequestScope request, Object parameterObject,
			Sql sql) {
		String sqlString = sql.getSql(request, parameterObject);
		return "SELECT COUNT(*) AS c FROM (" + sqlString + ") t";
	}

	private ResultMap getResultMap(RequestScope request,
			Object parameterObject, Sql sql) {
		return getResultMap();
	}

}
