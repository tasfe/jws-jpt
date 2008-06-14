package ${package}.service;

import ${package}.model.Resource;
import ${package}.model.User;

public interface CacheManager {

	public void updateResource(Resource resource, String origname);

	public void updateUser(User user, String origname);

}