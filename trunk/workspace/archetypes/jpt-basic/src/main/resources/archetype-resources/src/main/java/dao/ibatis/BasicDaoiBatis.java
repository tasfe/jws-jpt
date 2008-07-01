package ${package}.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapException;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
import ${package}.dao.Dao;
import ${package}.dao.ibatis.ext.CountStatementUtil;
import ${package}.model.helper.ParamsWrapper;

public class BasicDaoiBatis extends SqlMapClientDaoSupport implements Dao {

	private static final String PREV = "prev";

	public List list(Class clazz) {
		String query = iBatisDaoUtils.getSelectQuery(clazz);
		Object p = executeNextQuery(query, null, PREV);
		List result = getSqlMapClientTemplate().queryForList(query, p);
		executeNextQuery(query, p, null);
		return result;
	}

	public List list(String name) {
		String query = iBatisDaoUtils.getFindQuery(name);
		Object p = executeNextQuery(query, null, PREV);
		List result = getSqlMapClientTemplate().queryForList(query, p);
		executeNextQuery(query, p, null);
		return result;
	}

	public List find(Class clazz, Object parameter) {
		String query = iBatisDaoUtils.getSelectQuery(clazz);
		Object p = executeNextQuery(query, parameter, PREV);
		List result = getSqlMapClientTemplate().queryForList(query, p);
		executeNextQuery(query, p, null);
		return result;
	}

	public List find(String name, Object parameter) {
		String query = iBatisDaoUtils.getFindQuery(name);
		Object p = executeNextQuery(query, parameter, PREV);
		List result = getSqlMapClientTemplate().queryForList(query, p);
		executeNextQuery(query, p, null);
		return result;
	}

	public Object get(Class clazz, Object parameter) {
		String query = iBatisDaoUtils.getFindQuery(clazz);
		Object p = executeNextQuery(query, parameter, PREV);
		Object result = getSqlMapClientTemplate().queryForObject(query, p);
		executeNextQuery(query, p, null);
		return result;
	}

	public Object get(String name, Object parameter) {
		String query = iBatisDaoUtils.getFindQuery(name);
		Object p = executeNextQuery(query, parameter, PREV);
		Object result = getSqlMapClientTemplate().queryForObject(query, p);
		executeNextQuery(query, p, null);
		return result;
	}

	public List page(Class clazz, int offset, int limit) {
		String query = iBatisDaoUtils.getSelectQuery(clazz);
		Object p = executeNextQuery(query, null, PREV);
		List result = getSqlMapClientTemplate().queryForList(query, p, offset,
				limit);
		executeNextQuery(query, p, null);
		return result;
	}

	public List page(Class clazz, Object parameter, int offset, int limit) {
		String query = iBatisDaoUtils.getSelectQuery(clazz);
		Object p = executeNextQuery(query, parameter, PREV);
		List result = getSqlMapClientTemplate().queryForList(query, p, offset,
				limit);
		executeNextQuery(query, p, null);
		return result;
	}

	public List page(String name, int offset, int limit) {
		String query = iBatisDaoUtils.getFindQuery(name);
		Object p = executeNextQuery(query, null, PREV);
		List result = getSqlMapClientTemplate().queryForList(query, p, offset,
				limit);
		executeNextQuery(query, p, null);
		return result;
	}

	public List page(String name, Object parameter, int offset, int limit) {
		String query = iBatisDaoUtils.getFindQuery(name);
		Object p = executeNextQuery(query, parameter, PREV);
		List result = getSqlMapClientTemplate().queryForList(query, p, offset,
				limit);
		executeNextQuery(query, p, null);
		return result;
	}

	public List rand(Class clazz, int limit) {
		Object parameter = iBatisDaoUtils.buildRands(null, limit);
		String query = iBatisDaoUtils.getRandQuery(clazz);
		Object p = executeNextQuery(query, parameter, PREV);
		List result = getSqlMapClientTemplate().queryForList(query, p);
		executeNextQuery(query, p, null);
		return result;
	}

	public List rand(Class clazz, Object parameter, int limit) {
		parameter = iBatisDaoUtils.buildRands(parameter, limit);
		String query = iBatisDaoUtils.getRandQuery(clazz);
		Object p = executeNextQuery(query, parameter, PREV);
		List result = getSqlMapClientTemplate().queryForList(query, p);
		executeNextQuery(query, p, null);
		return result;
	}

	public List rand(String name, int limit) {
		Object parameter = iBatisDaoUtils.buildRands(null, limit);
		String query = iBatisDaoUtils.getFindQuery(name);
		Object p = executeNextQuery(query, parameter, PREV);
		List result = getSqlMapClientTemplate().queryForList(query, p);
		executeNextQuery(query, p, null);
		return result;
	}

	public List rand(String name, Object parameter, int limit) {
		parameter = iBatisDaoUtils.buildRands(parameter, limit);
		String query = iBatisDaoUtils.getFindQuery(name);
		Object p = executeNextQuery(query, parameter, PREV);
		List result = getSqlMapClientTemplate().queryForList(query, p);
		executeNextQuery(query, p, null);
		return result;
	}

	public void remove(Class clazz, Object parameter) {
		String query = iBatisDaoUtils.getDeleteQuery(clazz);
		Object p = executeNextQuery(query, parameter, PREV);
		getSqlMapClientTemplate().update(query, p);
		executeNextQuery(query, p, null);
	}

	public void remove(String name, Object parameter) {
		String query = iBatisDaoUtils.getDeleteQuery(name);
		Object p = executeNextQuery(query, parameter, PREV);
		getSqlMapClientTemplate().update(query, p);
		executeNextQuery(query, p, null);
	}

	public void saveOrUpdate(Object o) {
		Object p = null;
		if (o != null) {
			Class clazz = o.getClass();
			String query = null;
			if (iBatisDaoUtils.getPrimaryKey(o) == null) {
				query = iBatisDaoUtils.getInsertQuery(clazz);
				p = executeNextQuery(query, o, PREV);
				iBatisDaoUtils.setPrimaryKey(o, getSqlMapClientTemplate()
						.insert(query, p));
			} else {
				query = iBatisDaoUtils.getUpdateQuery(clazz);
				p = executeNextQuery(query, o, PREV);
				getSqlMapClientTemplate().update(query, p);
			}
			executeNextQuery(query, p, null);
		}
	}

	public void save(String name, Object o) {
		if (o != null) {
			String query = iBatisDaoUtils.getInsertQuery(name);
			Object p = executeNextQuery(query, o, PREV);
			iBatisDaoUtils.setPrimaryKey(o, getSqlMapClientTemplate().insert(
					query, p));
			executeNextQuery(query, p, null);
		}
	}

	@SuppressWarnings("unchecked")
	private Object executeNextQuery(String query, Object parameter,
			String prefix) {
		Object p = parameter;
		String[] nexts = null;

		Map parameters = new HashMap();
		parameters.put("p0", parameter);

		String ppname = "p";
		if (prefix != null) {
			ppname = prefix + "_p";
		}

		for (int i = 0; (nexts = iBatisDaoUtils.getNextQuery(query, i, prefix)) != null; i++) {
			if (queryExist(nexts[0])) {
				List result = getSqlMapClientTemplate().queryForList(nexts[0],
						parameters);
				if (result.size() == 1) {
					parameter = result.get(0);
				} else {
					parameter = result;
				}
				parameters.put(ppname + (i + 1), parameter);
				p = parameters;
			} else if (queryExist(nexts[1])) {
				getSqlMapClientTemplate().update(nexts[1], parameters);
				p = parameters;
			} else if (queryExist(nexts[2])) {
				getSqlMapClientTemplate().insert(nexts[2], parameters);
				p = parameters;
			} else {
				break;
			}
		}
		return p;
	}

	public void update(Object o) {
		if (o != null) {
			String query = iBatisDaoUtils.getUpdateQuery(o.getClass());
			Object p = executeNextQuery(query, o, PREV);
			getSqlMapClientTemplate().update(query, p);
			executeNextQuery(query, p, null);
		}
	}

	public void update(String name, Object o) {
		if (o != null) {
			String query = iBatisDaoUtils.getUpdateQuery(name);
			Object p = executeNextQuery(query, o, PREV);
			getSqlMapClientTemplate().update(query, p);
			executeNextQuery(query, p, null);
		}
	}

	public long total(Class clazz) {
		return getTotal(iBatisDaoUtils.getSelectQuery(clazz), null);
	}

	public long total(Class clazz, Object parameter) {
		return getTotal(iBatisDaoUtils.getSelectQuery(clazz), parameter);
	}

	public long total(String name) {
		return getTotal(iBatisDaoUtils.getFindQuery(name), null);
	}

	public long total(String name, Object parameter) {
		return getTotal(iBatisDaoUtils.getFindQuery(name), parameter);
	}

	protected long getTotal(String selectQuery, Object parameter) {
		prepareCountQuery(selectQuery);
		if (parameter instanceof ParamsWrapper) {
			ParamsWrapper params = (ParamsWrapper) parameter;
			boolean savedSortable = params.isSortable();
			params.setSortable(false);
			Long total = (Long) getSqlMapClientTemplate()
					.queryForObject(
							CountStatementUtil.getCountStatementId(selectQuery),
							params);
			params.setSortable(savedSortable);
			return total;
		}
		return (Long) getSqlMapClientTemplate().queryForObject(
				CountStatementUtil.getCountStatementId(selectQuery), parameter);
	}

	protected void prepareCountQuery(String selectQuery) {

		String countQuery = CountStatementUtil.getCountStatementId(selectQuery);
		if (logger.isDebugEnabled()) {
			logger.debug("Convert " + selectQuery + " to " + countQuery);
		}
		SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
		if (sqlMapClient instanceof ExtendedSqlMapClient) {
			SqlMapExecutorDelegate delegate = ((ExtendedSqlMapClient) sqlMapClient)
					.getDelegate();
			try {
				delegate.getMappedStatement(countQuery);
			} catch (SqlMapException e) {
				delegate.addMappedStatement(CountStatementUtil
						.createCountStatement(delegate
								.getMappedStatement(selectQuery)));
			}

		}
	}

	private boolean queryExist(String query) {
		SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
		if (sqlMapClient instanceof ExtendedSqlMapClient) {
			SqlMapExecutorDelegate delegate = ((ExtendedSqlMapClient) sqlMapClient)
					.getDelegate();
			try {
				delegate.getMappedStatement(query);
				return true;
			} catch (SqlMapException e) {
			}

		}
		return false;
	}

}
