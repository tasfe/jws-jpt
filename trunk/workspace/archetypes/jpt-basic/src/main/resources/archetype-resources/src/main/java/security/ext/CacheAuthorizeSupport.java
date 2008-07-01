package ${package}.security.ext;

import java.util.HashSet;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;

import ${package}.security.service.SecurityCacheManager;

public class CacheAuthorizeSupport extends AuthorizeSupport {

	private boolean convertUrlToLowercaseBeforeComparison = false;

	private SecurityCacheManager cacheManager;

	public void setConvertUrlToLowercaseBeforeComparison(
			boolean convertUrlToLowercaseBeforeComparison) {
		this.convertUrlToLowercaseBeforeComparison = convertUrlToLowercaseBeforeComparison;
	}

	public void setCacheManager(SecurityCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public Set<String> getRequiredAuthorities(String url) {
		if (isUseAntPath()) {
			int pos = url.lastIndexOf("?");
			if (pos != -1) {
				url = url.substring(0, pos);
			}
		}

		if (convertUrlToLowercaseBeforeComparison) {
			url = url.toLowerCase();
		}

		Set<String> result = new HashSet<String>();

		for (String pattern : cacheManager.getResourceUrls()) {
			if (isMatch(pattern, url)) {
				GrantedAuthority[] authorities = cacheManager
						.getResourceFromCache(pattern).getAuthorities();
				if (authorities != null) {
					for (GrantedAuthority authority : authorities) {
						result.add(authority.getAuthority());
					}
				}
			}
		}

		return result;
	}

}
