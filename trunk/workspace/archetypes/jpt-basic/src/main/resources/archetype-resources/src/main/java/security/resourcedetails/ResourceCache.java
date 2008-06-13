package ${package}.security.resourcedetails;

import java.util.List;

public interface ResourceCache {

	public ResourceDetails getResourceFromCache(String resstring);

	public void putResourceInCache(ResourceDetails resourceDetails);

	public void removeResourceFromCache(String resstring);

	public List<String> getResourceUrls();

}