package ${package}.security.service;

import java.util.List;

import ${package}.security.resourcedetails.ResourceDetails;

public interface SecurityCacheManager {

	public List<String> getResourceUrls();

	public ResourceDetails getResourceFromCache(String url);

}
