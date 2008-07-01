package ${package}.security.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

public class Struts2ActionFilter implements InitializingBean, Filter {

	protected static final Log logger = LogFactory
			.getLog(Struts2ActionFilter.class);

	private static final String FILTER_APPLIED = "__acegi_struts2_action_filter_applied";

	private String strutsActionExtension = "action";

	private String actionMethodMatchFormats[] = {
			"^/server/pages/\\w+/\\w+-${method}-\\w+(/\\d+)?\\.(json|html|jsp)$",
			"^/server/pages/\\w+/${method}(-\\w+)?(/\\d+)?\\.(json|html|jsp)$" };

	public String getStrutsActionExtension() {
		return strutsActionExtension;
	}

	public void setStrutsActionExtension(String strutsActionExtension) {
		this.strutsActionExtension = strutsActionExtension;
	}

	public String[] getActionMethodMatchFormats() {
		return actionMethodMatchFormats;
	}

	public void setActionMethodMatchFormats(String[] actionMethodMatchFormats) {
		this.actionMethodMatchFormats = actionMethodMatchFormats;
	}

	public void afterPropertiesSet() throws Exception {
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		if ((servletRequest != null)
				&& (servletRequest.getAttribute(FILTER_APPLIED) != null)) {
			chain.doFilter(servletRequest, servletResponse);
		} else {
			if (servletRequest != null) {
				servletRequest.setAttribute(FILTER_APPLIED, Boolean.TRUE);
			}
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;

			String uri = getUri(request);
			if (uri.endsWith("." + strutsActionExtension)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

			String method = null;
			for (Enumeration e = request.getParameterNames(); e
					.hasMoreElements();) {
				String name = (String) e.nextElement();
				if (name.startsWith("method:")) {
					if (method == null) {
						method = name.substring(7);
					} else if (!method.equals(name.substring(7))) {
						response.sendError(HttpServletResponse.SC_FORBIDDEN);
						return;
					}
				}
			}
			if (method != null) {
				boolean matched = false;
				for (String format : actionMethodMatchFormats) {
					String regex = StringUtils.replace(format, "${method}",
							method);
					if (uri.matches(regex)) {
						matched = true;
						break;
					}
				}
				if (!matched) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
					return;
				}

			}

			chain.doFilter(servletRequest, servletResponse);
		}
	}

	private String getUri(HttpServletRequest request) {
		String uri = request.getServletPath();
		if (uri == null) {
			uri = request.getRequestURI().substring(
					request.getContextPath().length());
		}
		String pathInfo = request.getPathInfo();
		return uri + ((pathInfo == null) ? "" : pathInfo);
	}

}
