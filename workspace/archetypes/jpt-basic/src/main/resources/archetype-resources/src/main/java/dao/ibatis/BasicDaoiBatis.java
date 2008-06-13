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

	public List list(Class clazz) {
		return getSqlMapClientTemplate().queryForList(
				iBatisDaoUtils.getSelectQuery(clazz), null);
	}

	public List list(String name) {
		return getSqlMapClientTemplate().queryForList(
				iBatisDaoUtils.getFindQuery(name), null);
	}

	public List find(Class clazz, Object parameter) {
		return getSqlMapClientTemplate().queryForList(
				iBatisDaoUtils.getSelectQuery(clazz), parameter);
	}

	public List find(String name, Object parameter) {
		return getSqlMapClientTemplate().queryForList(
				iBatisDaoUtils.getFindQuery(name), parameter);
	}

	public Object get(Class clazz, Object parameter) {
		return getSqlMapClientTemplate().queryForObject(
				iBatisDaoUtils.getFindQuery(clazz), parameter);
	}

	public Object get(String name, Object parameter) {
		return getSqlMapClientTemplate().queryForObject(
				iBatisDaoUtils.getFindQuery(name), parameter);
	}

	public List page(Class clazz, int offset, int limit) {
		return getSqlMapClientTemplate().queryForList(
				iBatisDaoUtils.getSelectQuery(clazz), null, offset, limit);
	}

	public List page(Class clazz, Object parameter, int offset, int limit) {
		return getSqlMapClientTemplate().queryForList(
				iBatisDaoUtils.getSelectQuery(clazz), parameter, offset, limit);
	}

	public List page(String name, int offset, int limit) {
		return getSqlMapClientTemplate().queryForList(
				iBatisDaoUtils.getFindQuery(name), null, offset, limit);
	}

	public List page(String name, Object parameter, int offset, int limit) {
		return getSqlMapClientTemplate().queryForList(
				iBatisDaoUtils.getFindQuery(name), parameter, offset, limit);
	}

	public List rand(Class clazz, int limit) {
		return getSqlMapClientTemplate().queryForList(
				iBatisDaoUtils.getRandQuery(clazz),
				iBatisDaoUtils.buildRands(null, limit));
	}

	public List rand(Class clazz, Object parameter, int limit) {
		return getSqlMapClientTemplate().queryForList(
				iBatisDaoUtils.getRandQuery(clazz),
				iBatisDaoUtils.buildRands(parameter, limit));
	}

	public List rand(String name, int limit) {
		return getSqlMapClientTemplate().queryForList(
				iBatisDaoUtils.getFindQuery(name),
				iBatisDaoUtils.buildRands(null, limit));
	}

	public List rand(String name, Object parameter, int limit) {
		return getSqlMapClientTemplate().queryForList(
				iBatisDaoUtils.getFindQuery(name),
				iBatisDaoUtils.buildRands(parameter, limit));
	}

	public void remove(Class clazz, Object parameter) {
		String query = iBatisDaoUtils.getDeleteQuery(clazz);
		getSqlMapClientTemplate().update(query, parameter);
		executeNextQuery(query, parameter);
	}

	public void remove(String name, Object parameter) {
		String query = iBatisDaoUtils.getDeleteQuery(name);
		getSqlMapClientTemplate().update(query, parameter);
		executeNextQuery(query, parameter);
	}

	public void saveOrUpdate(Object o) {
		if (o != null) {
			Class clazz = o.getClass();
			String query = null;
			if (iBatisDaoUtils.getPrimaryKey(o) == null) {
				query = iBatisDaoUtils.getInsertQuery(clazz);
				iBatisDaoUtils.setPrimaryKey(o, getSqlMapClientTemplate()
						.insert(query, o));
			} else {
				query = iBatisDaoUtils.getUpdateQuery(clazz);
				getSqlMapClientTemplate().update(query, o);
			}
			executeNextQuery(query, o);
		}
	}

	public void save(String name, Object o) {
		if (o != null) {
			String query = iBatisDaoUtils.getInsertQuery(name);
			iBatisDaoUtils.setPrimaryKey(o, getSqlMapClientTemplate().insert(
					query, o));
			executeNextQuery(query, o);
		}
	}

	@SuppressWarnings("unchecked")
	private void executeNextQuery(String query, Object parameter) {
		String[] nexts = null;
		Map parameters = new HashMap();
		parameters.put("p0", parameter);

		for (int i = 0; (nexts = iBatisDaoUtils.getNextQuery(query, i)) != null; i++) {
			if (queryExist(nexts[0])) {
				List result = getSqlMapClientTemplate().queryForList(nexts[0],
						parameters);
				if (result.size() == 1) {
					parameter = result.get(0);
				} else {
					parameter = result;
				}
				parameters.put("p" + (i + 1), parameter);
			} else if (queryExist(nexts[1])) {
				getSqlMapClientTemplate().update(nexts[1], parameters);
			} else if (queryExist(nexts[2])) {
				getSqlMapClientTemplate().insert(nexts[2], parameters);
			} else {
				return;
			}
		}
	}

	public void update(Object o) {
		if (o != null) {
			String query = iBatisDaoUtils.getUpdateQuery(o.getClass());
			getSqlMapClientTemplate().update(query, o);
			executeNextQuery(query, o);
		}
	}

	public void update(String name, Object o) {
		if (o != null) {
			String query = iBatisDaoUtils.getUpdateQuery(name);
			getSqlMapClientTemplate().update(query, o);
			executeNextQuery(query, o);
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
