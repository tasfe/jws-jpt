package ${package}.security.ext;

import java.util.HashSet;
import java.util.Set;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public abstract class AuthorizeSupport {

	protected final Log logger = LogFactory.getLog(getClass());

	private boolean useAntPath = false;

	private PathMatcher pathMatcher = new AntPathMatcher();

	private PatternMatcher matcher = new Perl5Matcher();

	public boolean isUseAntPath() {
		return useAntPath;
	}

	public void setUseAntPath(boolean useAntPath) {
		this.useAntPath = useAntPath;
	}

	public boolean isMatch(String pattern, String url) {
		if (isUseAntPath()) {
			return pathMatcher.match(pattern, url);
		}
		try {
			return matcher.matches(url, new Perl5Compiler().compile(pattern,
					Perl5Compiler.READ_ONLY_MASK));
		} catch (MalformedPatternException mpe) {
			throw new IllegalArgumentException("Malformed regular expression: "
					+ pattern);
		}
	}

	public boolean authorize(String url) {
		if ((url != null) && !"".equals(url)) {
			Set<String> granted = getPrincipalAuthorities();
			Set<String> required = getRequiredAuthorities(url);
			granted.retainAll(required);
			if (granted.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public Set<String> getPrincipalAuthorities() {
		Set<String> result = new HashSet<String>();
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			GrantedAuthority[] authorities = auth.getAuthorities();
			if (authorities != null) {
				for (GrantedAuthority authority : authorities) {
					result.add(authority.getAuthority());
				}
				return result;
			}
		}
		return result;
	}

	public abstract Set<String> getRequiredAuthorities(String url);

}