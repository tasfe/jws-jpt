package ${package}.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import ${package}.dao.Dao;
import ognl.Ognl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ClassUtils;

public abstract class BaseManager<T> {

	protected transient final Log logger = LogFactory.getLog(getClass());

	protected static final String ENTITY_CLASS = "entityClass";

	protected Dao dao;

	protected Class<T> entityClass;

	public interface Executor {
		public Object execute();
	}

	@SuppressWarnings("unchecked")
	public BaseManager() {
		Type type = ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		if (type instanceof Class) {
			entityClass = (Class<T>) type;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("entityClass is " + entityClass);
		}
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public List list(String name) throws Exception {
		try {
			return dao.list(name);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage(), e.getCause());
			}
			throw e;
		}
	}

	public List page(String name, int offset, int limit) throws Exception {
		try {
			return dao.page(name, offset, limit);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage(), e.getCause());
			}
			throw e;
		}
	}

	public List rand(String name, int limit) throws Exception {
		try {
			return dao.rand(name, limit);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage(), e.getCause());
			}
			throw e;
		}
	}

	public long total(String name) throws Exception {
		try {
			return dao.total(name);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage(), e.getCause());
			}
			throw e;
		}
	}

	protected Object executeByEntityName(String action, Class clazz,
			Executor defaultExecutor) throws Exception {
		if (isCompatibleClass(clazz)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + ClassUtils.getShortName(entityClass));
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		return defaultExecutor.execute();
	}

	protected Object executeByEntityName(String action, Class clazz,
			int offset, int limit, Executor defaultExecutor) throws Exception {
		if (isCompatibleClass(clazz)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + ClassUtils.getShortName(entityClass),
						int.class, int.class);
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, offset, limit);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		return defaultExecutor.execute();
	}

	protected Object executeByEntityName(String action, Class clazz,
			Object parameter, Executor defaultExecutor) throws Exception {
		if (isCompatibleClass(clazz)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + ClassUtils.getShortName(entityClass),
						Object.class);
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, parameter);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		return defaultExecutor.execute();
	}

	protected Object executeByEntityName(String action, Class clazz,
			Object parameter, int limit, Executor defaultExecutor)
			throws Exception {
		if (isCompatibleClass(clazz)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + ClassUtils.getShortName(entityClass),
						Object.class, int.class);
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, parameter, limit);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		return defaultExecutor.execute();
	}

	protected Object executeByEntityName(String action, Class clazz,
			Object parameter, int offset, int limit, Executor defaultExecutor)
			throws Exception {
		if (isCompatibleClass(clazz)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + ClassUtils.getShortName(entityClass),
						Object.class, int.class, int.class);
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, parameter, offset, limit);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		return defaultExecutor.execute();
	}

	protected Object executeByEntityName(String action, Object entity,
			Executor defaultExecutor) throws Exception {
		if (isCompatibleEntity(entity)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + ClassUtils.getShortName(entityClass),
						entityClass);
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, entity);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		return defaultExecutor.execute();
	}

	protected Object executeByEntityName(String action, String name,
			Object entity, Executor defaultExecutor) throws Exception {
		if (isCompatibleEntity(entity)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + ClassUtils.getShortName(entityClass),
						String.class, entityClass);

			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, name, entity);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		return defaultExecutor.execute();
	}

	protected Object executeByMethodName(String action, String name,
			Object parameter, Executor defaultExecutor) throws Exception {
		if (isCompatibleEntity(parameter)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + StringUtils.capitalize(name), entityClass);

			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, parameter);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		if (isCompatibleMap(parameter)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + StringUtils.capitalize(name), Map.class);

			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, parameter);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		if (isCompatibleObject(parameter)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + StringUtils.capitalize(name), Object.class);

			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, parameter);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		return defaultExecutor.execute();
	}

	protected Object executeByMethodName(String action, String name,
			Object parameter, int limit, Executor defaultExecutor)
			throws Exception {
		if (isCompatibleEntity(parameter)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + StringUtils.capitalize(name), entityClass,
						int.class);

			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, parameter, limit);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		if (isCompatibleMap(parameter)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + StringUtils.capitalize(name), Map.class,
						int.class);

			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, parameter, limit);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		if (isCompatibleObject(parameter)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + StringUtils.capitalize(name), Object.class,
						int.class);

			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, parameter, limit);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		return defaultExecutor.execute();
	}

	protected Object executeByMethodName(String action, String name,
			Object parameter, int offset, int limit, Executor defaultExecutor)
			throws Exception {
		if (isCompatibleEntity(parameter)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + StringUtils.capitalize(name), entityClass,
						int.class, int.class);

			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, parameter, offset, limit);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		if (isCompatibleMap(parameter)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + StringUtils.capitalize(name), Map.class,
						int.class, int.class);

			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, parameter, offset, limit);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}

		if (isCompatibleObject(parameter)) {
			Method method = null;
			try {
				method = getClass().getMethod(
						action + StringUtils.capitalize(name), Object.class,
						int.class, int.class);

			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage(), e.getCause());
				}
			}
			if (method != null) {
				try {
					return method.invoke(this, parameter, offset, limit);
				} catch (InvocationTargetException e) {
					Throwable target = e.getTargetException();
					if (target instanceof Exception) {
						throw (Exception) target;
					} else {
						throw new Exception(target);
					}
				}
			}
		}
		return defaultExecutor.execute();
	}

	protected boolean isCompatibleClass(Class clazz) {
		if (entityClass == null || clazz == null) {
			return false;
		}
		return entityClass.isAssignableFrom(clazz);
	}

	protected boolean isCompatibleEntity(Object parameter) {
		if (entityClass == null || parameter == null) {
			return false;
		}
		return entityClass.isAssignableFrom(parameter.getClass());
	}

	protected boolean isCompatibleMap(Object parameter) {
		if (entityClass == null || parameter == null) {
			return false;
		}
		return (parameter instanceof Map)
				&& entityClass.isAssignableFrom((Class) ((Map) parameter)
						.get(ENTITY_CLASS));
	}

	protected boolean isCompatibleObject(Object parameter) {
		if (entityClass == null || parameter == null) {
			return false;
		}
		Object clazz = null;
		try {
			clazz = Ognl.getValue(ENTITY_CLASS, parameter);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage(), e.getCause());
			}
		}
		return (clazz instanceof Class)
				&& entityClass.isAssignableFrom((Class) clazz);
	}

	protected boolean isCompatibleName(String name) {
		return false;
	}

}
