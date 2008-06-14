package ${package}.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ${package}.Constants;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.HttpSessionContextIntegrationFilter;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.acegisecurity.providers.dao.UserCache;
import org.acegisecurity.providers.dao.cache.NullUserCache;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

public class HttpSessionContextIntegrationForCacheFilter implements
		InitializingBean, Filter {

	protected static final Log logger = LogFactory
			.getLog(HttpSessionContextIntegrationForCacheFilter.class);

	private static final String FILTER_APPLIED = "__acegi_session_integration_filter_applied";

	private UserCache userCache = new NullUserCache();

	private String authorityResetUrl;

	private Class context = SecurityContextImpl.class;

	private Object contextObject;

	private boolean allowSessionCreation = true;

	private boolean forceEagerSessionCreation = false;

	public void afterPropertiesSet() throws Exception {
		if ((this.context == null)
				|| (!SecurityContext.class.isAssignableFrom(this.context))) {
			throw new IllegalArgumentException(
					"context must be defined and implement SecurityContext (typically use org.acegisecurity.context.SecurityContextImpl; existing class is "
							+ this.context + ")");
		}

		if ((forceEagerSessionCreation == true)
				&& (allowSessionCreation == false)) {
			throw new IllegalArgumentException(
					"If using forceEagerSessionCreation, you must set allowSessionCreation to also be true");
		}

		this.contextObject = generateNewContext();
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if ((request != null) && (request.getAttribute(FILTER_APPLIED) != null)) {
			chain.doFilter(request, response);
		} else {
			if (request != null) {
				request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
			}

			HttpSession httpSession = null;
			boolean httpSessionExistedAtStartOfRequest = false;

			try {
				httpSession = ((HttpServletRequest) request)
						.getSession(forceEagerSessionCreation);
			} catch (IllegalStateException ignored) {
			}

			if (httpSession != null) {
				httpSessionExistedAtStartOfRequest = true;

				Object contextFromSessionObject = httpSession
						.getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY);

				if (contextFromSessionObject != null) {
					if (contextFromSessionObject instanceof SecurityContext) {

						SecurityContext securityContext = (SecurityContext) contextFromSessionObject;
						if (Constants.isRelogin()) {

							Authentication auth = securityContext
									.getAuthentication();
							if (auth != null) {
								Object principal = auth.getPrincipal();
								if (principal instanceof UserDetails) {
									UserDetails ud1 = (UserDetails) principal;
									UserDetails ud2 = userCache
											.getUserFromCache(ud1.getUsername());
									if (!equalsForUser(ud1, ud2)) {
										httpSession
												.removeAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY);
										SecurityContextHolder.clearContext();
										HttpServletRequest httpRequest = (HttpServletRequest) request;
										HttpServletResponse httpResponse = (HttpServletResponse) response;
										httpResponse.sendRedirect(httpResponse
												.encodeRedirectURL(httpRequest
														.getContextPath()
														+ authorityResetUrl));
										return;
									}
								}
							}
						}
						if (logger.isDebugEnabled()) {
							logger
									.debug("Obtained from ACEGI_SECURITY_CONTEXT a valid SecurityContext and set to SecurityContextHolder: '"
											+ securityContext + "'");
						}

						SecurityContextHolder.setContext(securityContext);
					} else {
						if (logger.isWarnEnabled()) {
							logger
									.warn("ACEGI_SECURITY_CONTEXT did not contain a SecurityContext but contained: '"
											+ contextFromSessionObject
											+ "'; are you improperly modifying the HttpSession directly (you should always use SecurityContextHolder) or using the HttpSession attribute reserved for this class? - new SecurityContext instance associated with SecurityContextHolder");
						}

						SecurityContextHolder.setContext(generateNewContext());
					}
				} else {
					if (logger.isDebugEnabled()) {
						logger
								.debug("HttpSession returned null object for ACEGI_SECURITY_CONTEXT - new SecurityContext instance associated with SecurityContextHolder");
					}

					SecurityContextHolder.setContext(generateNewContext());
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger
							.debug("No HttpSession currently exists - new SecurityContext instance associated with SecurityContextHolder");
				}

				SecurityContextHolder.setContext(generateNewContext());
			}

			// Make the HttpSession null, as we want to ensure we don't keep
			// a reference to the HttpSession laying around in case the
			// chain.doFilter() invalidates it.
			httpSession = null;

			// Proceed with chain
			int contextWhenChainProceeded = SecurityContextHolder.getContext()
					.hashCode();

			try {
				chain.doFilter(request, response);
			} catch (IOException ioe) {
				throw ioe;
			} catch (ServletException se) {
				throw se;
			} finally {
				// do clean up, even if there was an exception
				// Store context back to HttpSession
				try {
					httpSession = ((HttpServletRequest) request)
							.getSession(false);
				} catch (IllegalStateException ignored) {
				}

				if ((httpSession == null) && httpSessionExistedAtStartOfRequest) {
					if (logger.isDebugEnabled()) {
						logger
								.debug("HttpSession is now null, but was not null at start of request; session was invalidated, so do not create a new session");
					}
				}

				// Generate a HttpSession only if we need to
				if ((httpSession == null)
						&& !httpSessionExistedAtStartOfRequest) {
					if (!allowSessionCreation) {
						if (logger.isDebugEnabled()) {
							logger
									.debug("The HttpSession is currently null, and the HttpSessionContextIntegrationFilter is prohibited from creating a HttpSession (because the allowSessionCreation property is false) - SecurityContext thus not stored for next request");
						}
					} else if (!contextObject.equals(SecurityContextHolder
							.getContext())) {
						if (logger.isDebugEnabled()) {
							logger
									.debug("HttpSession being created as SecurityContextHolder contents are non-default");
						}

						try {
							httpSession = ((HttpServletRequest) request)
									.getSession(true);
						} catch (IllegalStateException ignored) {
						}
					} else {
						if (logger.isDebugEnabled()) {
							logger
									.debug("HttpSession is null, but SecurityContextHolder has not changed from default: ' "
											+ SecurityContextHolder
													.getContext()
											+ "'; not creating HttpSession or storing SecurityContextHolder contents");
						}
					}
				}

				if ((httpSession != null)
						&& (SecurityContextHolder.getContext().hashCode() != contextWhenChainProceeded)) {
					httpSession
							.setAttribute(
									HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY,
									SecurityContextHolder.getContext());

					if (logger.isDebugEnabled()) {
						logger.debug("SecurityContext stored to HttpSession: '"
								+ SecurityContextHolder.getContext() + "'");
					}
				}

				SecurityContextHolder.clearContext();

				if (logger.isDebugEnabled()) {
					logger
							.debug("SecurityContextHolder set to new context, as request processing completed");
				}
			}
		}
	}

	public SecurityContext generateNewContext() throws ServletException {
		try {
			return (SecurityContext) this.context.newInstance();
		} catch (InstantiationException ie) {
			throw new ServletException(ie);
		} catch (IllegalAccessException iae) {
			throw new ServletException(iae);
		}
	}

	public Class getContext() {
		return context;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public boolean isAllowSessionCreation() {
		return allowSessionCreation;
	}

	public boolean isForceEagerSessionCreation() {
		return forceEagerSessionCreation;
	}

	public void setAllowSessionCreation(boolean allowSessionCreation) {
		this.allowSessionCreation = allowSessionCreation;
	}

	public void setContext(Class secureContext) {
		this.context = secureContext;
	}

	public void setForceEagerSessionCreation(boolean forceEagerSessionCreation) {
		this.forceEagerSessionCreation = forceEagerSessionCreation;
	}

	public UserCache getUserCache() {
		return userCache;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public String getAuthorityResetUrl() {
		return authorityResetUrl;
	}

	public void setAuthorityResetUrl(String authorityResetUrl) {
		this.authorityResetUrl = authorityResetUrl;
	}

	private boolean equalsForUser(UserDetails user1, UserDetails user2) {
		if (user1 == user2) {
			return true;
		}
		if (user1 == null || user2 == null) {
			return false;
		}

		if (user1.isEnabled() != user2.isEnabled()) {
			return false;
		}

		GrantedAuthority[] auth1 = user1.getAuthorities();
		GrantedAuthority[] auth2 = user2.getAuthorities();
		if ((auth1 != auth2) && (auth1 == null || auth2 == null)) {
			return false;
		}
		if (auth1.length != auth2.length) {
			return false;
		}

		for (int i = 0; i < auth1.length; i++) {
			if (!auth1[i].equals(auth2[i])) {
				return false;
			}
		}

		return true;
	}

}
