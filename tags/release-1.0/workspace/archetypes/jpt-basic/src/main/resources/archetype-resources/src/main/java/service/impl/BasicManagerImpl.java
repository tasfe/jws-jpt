package ${package}.service.impl;

import java.util.Collection;
import java.util.List;

import ${package}.service.Manager;

public class BasicManagerImpl<T> extends BaseManager<T> implements Manager {

	protected static final String LIST = "list";

	protected static final String FIND = "find";

	protected static final String PAGE = "page";

	protected static final String RAND = "rand";

	protected static final String TOTAL = "total";

	protected static final String GET = "get";

	protected static final String REMOVE = "remove";

	protected static final String SAVE = "save";

	protected static final String UPDATE = "update";

	protected static final String SAVE_OR_UPDATE = "saveOrUpdate";

	public List find(final Class clazz, final Object parameter)
			throws Exception {
		return (List) executeByEntityName(FIND, clazz, parameter,
				new Executor() {
					public Object execute() {
						return dao.find(clazz, parameter);
					}
				});
	}

	public List find(final String name, final Object parameter)
			throws Exception {
		return (List) executeByMethodName(FIND, name, parameter,
				new Executor() {
					public Object execute() {
						return dao.find(name, parameter);
					}
				});
	}

	public Object get(final Class clazz, final Object parameter)
			throws Exception {
		return executeByEntityName(GET, clazz, parameter, new Executor() {
			public Object execute() {
				return dao.get(clazz, parameter);
			}
		});
	}

	public Object get(final String name, final Object parameter)
			throws Exception {
		return executeByEntityName(GET, name, parameter, new Executor() {
			public Object execute() {
				return dao.get(name, parameter);
			}
		});
	}

	public List list(final Class clazz) throws Exception {
		return (List) executeByEntityName(LIST, clazz, new Executor() {
			public Object execute() {
				return dao.list(clazz);
			}
		});
	}

	public List page(final Class clazz, final int offset, final int limit)
			throws Exception {
		return (List) executeByEntityName(PAGE, clazz, offset, limit,
				new Executor() {
					public Object execute() {
						return dao.page(clazz, offset, limit);
					}
				});
	}

	public List page(final Class clazz, final Object parameter,
			final int offset, final int limit) throws Exception {
		return (List) executeByEntityName(PAGE, clazz, parameter, offset,
				limit, new Executor() {
					public Object execute() {
						return dao.page(clazz, parameter, offset, limit);
					}
				});
	}

	public List page(final String name, final Object parameter,
			final int offset, final int limit) throws Exception {
		return (List) executeByMethodName(PAGE, name, parameter, offset, limit,
				new Executor() {
					public Object execute() {
						return dao.page(name, parameter, offset, limit);
					}
				});
	}

	public List rand(final Class clazz, final int limit) throws Exception {
		return (List) executeByEntityName(RAND, clazz, limit, new Executor() {
			public Object execute() {
				return dao.rand(clazz, limit);
			}
		});

	}

	public List rand(final Class clazz, final Object parameter, final int limit)
			throws Exception {
		return (List) executeByEntityName(RAND, clazz, parameter, limit,
				new Executor() {
					public Object execute() {
						return dao.rand(clazz, parameter, limit);
					}
				});

	}

	public List rand(final String name, final Object parameter, final int limit)
			throws Exception {
		return (List) executeByMethodName(RAND, name, parameter, limit,
				new Executor() {
					public Object execute() {
						return dao.rand(name, parameter, limit);
					}
				});
	}

	public long total(final Class clazz) throws Exception {
		return (Long) executeByEntityName(TOTAL, clazz, new Executor() {
			public Object execute() {
				return dao.total(clazz);
			}
		});

	}

	public long total(final Class clazz, final Object parameter)
			throws Exception {
		return (Long) executeByEntityName(TOTAL, clazz, parameter,
				new Executor() {
					public Object execute() {
						return dao.total(clazz, parameter);
					}
				});
	}

	public long total(final String name, final Object parameter)
			throws Exception {
		return (Long) executeByEntityName(TOTAL, name, parameter,
				new Executor() {
					public Object execute() {
						return dao.total(name, parameter);
					}
				});
	}

	public void remove(final Class clazz, final Object parameter)
			throws Exception {
		executeByEntityName(REMOVE, clazz, parameter, new Executor() {
			public Object execute() {
				dao.remove(clazz, parameter);
				return null;
			}
		});
	}

	public void remove(final String name, final Object parameter)
			throws Exception {
		executeByMethodName(REMOVE, name, parameter, new Executor() {
			public Object execute() {
				dao.remove(name, parameter);
				return null;
			}
		});
	}

	public void save(final String name, final Object entity) throws Exception {
		if (entity instanceof Collection) {
			Collection entities = (Collection) entity;
			for (final Object o : entities) {
				executeByEntityName(SAVE, name, o, new Executor() {
					public Object execute() {
						dao.save(name, o);
						return null;
					}
				});
			}
		} else {
			executeByEntityName(SAVE, name, entity, new Executor() {
				public Object execute() {
					dao.save(name, entity);
					return null;
				}
			});
		}
	}

	public void saveOrUpdate(final Object entity) throws Exception {
		if (entity instanceof Collection) {
			Collection entities = (Collection) entity;
			for (final Object o : entities) {
				executeByEntityName(SAVE_OR_UPDATE, o, new Executor() {
					public Object execute() {
						dao.saveOrUpdate(o);
						return null;
					}
				});
			}

		} else {
			executeByEntityName(SAVE_OR_UPDATE, entity, new Executor() {
				public Object execute() {
					dao.saveOrUpdate(entity);
					return null;
				}
			});
		}
	}

	public void update(final Object entity) throws Exception {
		if (entity instanceof Collection) {
			Collection entities = (Collection) entity;
			for (final Object o : entities) {
				executeByEntityName(UPDATE, o, new Executor() {
					public Object execute() {
						dao.update(o);
						return null;
					}
				});
			}

		} else {
			executeByEntityName(UPDATE, entity, new Executor() {
				public Object execute() {
					dao.update(entity);
					return null;
				}
			});
		}
	}

	public void update(final String name, final Object entity) throws Exception {
		if (entity instanceof Collection) {
			Collection entities = (Collection) entity;
			for (final Object o : entities) {
				executeByEntityName(UPDATE, name, o, new Executor() {
					public Object execute() {
						dao.update(name, o);
						return null;
					}
				});
			}
		} else {
			executeByEntityName(UPDATE, name, entity, new Executor() {
				public Object execute() {
					dao.update(name, entity);
					return null;
				}
			});
		}
	}

}
