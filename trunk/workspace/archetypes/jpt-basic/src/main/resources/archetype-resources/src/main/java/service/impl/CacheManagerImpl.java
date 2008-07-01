package ${package}.service.impl;

import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.providers.dao.UserCache;

import ${package}.dao.Dao;
import ${package}.model.Resource;
import ${package}.model.User;
import ${package}.model.helper.SecurityHelper;
import ${package}.security.resourcedetails.ResourceCache;
import ${package}.security.resourcedetails.ResourceDetails;
import ${package}.security.service.SecurityCacheManager;
import ${package}.service.CacheManager;
import ${package}.service.ModelStatics;

public class CacheManagerImpl implements CacheManager, SecurityCacheManager,
		ModelStatics {

	private ResourceCache resourceCache;

	private UserCache userCache;

	private Dao dao;

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public void setResourceCache(ResourceCache resourceCache) {
		this.resourceCache = resourceCache;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public void initialize() throws Exception {
		for (Object obj : dao.list(Resource.class)) {
			Resource resource = (Resource) obj;
			putResource(resource);
		}
	}

	public List<String> getResourceUrls() {
		return resourceCache.getResourceUrls();
	}

	public ResourceDetails getResourceFromCache(String url) {
		return resourceCache.getResourceFromCache(url);
	}

	public void updateUser(User user, String origname) {
		if (userCache.getUserFromCache(origname) != null)
			userCache.removeUserFromCache(origname);
		putUser(user);
	}

	public void updateResource(Resource resource, String origname) {
		if (resourceCache.getResourceFromCache(origname) != null)
			resourceCache.removeResourceFromCache(origname);
		putResource(resource);
	}

	private void putUser(User user) {
		if (user != null) {
			GrantedAuthority[] authorities = SecurityHelper.p2ga(dao.find(
					M_PERMIS_BY_USER, user.getId()));
			userCache.putUserInCache(SecurityHelper.u2ud(user, authorities));
		}
	}

	private void putResource(Resource resource) {
		if (resource != null) {
			GrantedAuthority[] authorities = SecurityHelper.p2ga(dao.find(
					M_PERMIS_BY_RESOURCE, resource.getId()));
			resourceCache.putResourceInCache(SecurityHelper.r2rd(resource,
					authorities));
		}
	}

}
