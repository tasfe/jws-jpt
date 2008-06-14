package ${package}.security.resourcedetails;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.util.Assert;

public class EhCacheBasedResourceCache implements ResourceCache,
		InitializingBean {

	private static final Log logger = LogFactory
			.getLog(EhCacheBasedResourceCache.class);

	private Cache cache;

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public Cache getCache() {
		return this.cache;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache, "cache mandatory");
	}

	public ResourceDetails getResourceFromCache(String url) {
		Element element = null;

		try {
			element = cache.get(url);
		} catch (CacheException e) {
			throw new DataRetrievalFailureException("Cache failure: "
					+ e.getMessage());
		}

		if (element != null) {
			return (ResourceDetails) element.getValue();
		}
		return null;
	}

	public void putResourceInCache(ResourceDetails resourceDetails) {
		Element element = new Element(resourceDetails.getUrl(), resourceDetails);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}
		cache.put(element);
	}

	public void removeResourceFromCache(String resstring) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resstring);
		}
		cache.remove(resstring);
	}

	@SuppressWarnings("unchecked")
	public List<String> getResourceUrls() {
		try {
			return cache.getKeys();
		} catch (IllegalStateException e) {
			throw new IllegalStateException(e.getMessage());
		} catch (CacheException e) {
			throw new UnsupportedOperationException(e.getMessage());
		}
	}

}
