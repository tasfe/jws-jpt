package ${package}.service;

import java.util.List;

public interface Manager {

	public List list(Class clazz) throws Exception;

	public List list(String name) throws Exception;

	public List find(Class clazz, Object parameter) throws Exception;

	public List find(String name, Object parameter) throws Exception;

	public Object get(Class clazz, Object parameter) throws Exception;

	public Object get(String name, Object parameter) throws Exception;

	public List page(Class clazz, int offset, int limit) throws Exception;

	public List page(Class clazz, Object parameter, int offset, int limit)
			throws Exception;

	public List page(String name, int offset, int limit) throws Exception;

	public List page(String name, Object parameter, int offset, int limit)
			throws Exception;

	public List rand(Class clazz, int limit) throws Exception;

	public List rand(Class clazz, Object parameter, int limit) throws Exception;

	public List rand(String name, int limit) throws Exception;

	public List rand(String name, Object parameter, int limit) throws Exception;

	public void remove(Class clazz, Object parameter) throws Exception;

	public void remove(String name, Object parameter) throws Exception;

	public void saveOrUpdate(Object entity) throws Exception;

	public void save(String name, Object entity) throws Exception;

	public void update(Object entity) throws Exception;

	public void update(String name, Object entity) throws Exception;

	public long total(Class clazz) throws Exception;

	public long total(Class clazz, Object parameter) throws Exception;

	public long total(String name) throws Exception;

	public long total(String name, Object parameter) throws Exception;

}
