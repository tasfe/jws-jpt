package ${package}.dao;

import java.util.List;

public interface Dao {

	public List list(Class clazz);

	public List list(String name);

	public List find(Class clazz, Object parameter);

	public List find(String name, Object parameter);

	public Object get(Class clazz, Object parameter);

	public Object get(String name, Object parameter);

	public List page(Class clazz, int offset, int limit);

	public List page(Class clazz, Object parameter, int offset, int limit);

	public List page(String name, int offset, int limit);

	public List page(String name, Object parameter, int offset, int limit);

	public List rand(Class clazz, int limit);

	public List rand(Class clazz, Object parameter, int limit);

	public List rand(String name, int limit);

	public List rand(String name, Object parameter, int limit);

	public void remove(Class clazz, Object parameter);

	public void remove(String name, Object parameter);

	public void saveOrUpdate(Object o);

	public void save(String name, Object o);

	public void update(Object o);

	public void update(String name, Object o);

	public long total(Class clazz);

	public long total(Class clazz, Object parameter);

	public long total(String name);

	public long total(String name, Object parameter);

}