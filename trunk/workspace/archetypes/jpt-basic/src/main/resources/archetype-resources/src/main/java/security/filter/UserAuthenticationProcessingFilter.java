package ${package}.security.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.event.authentication.InteractiveAuthenticationSuccessEvent;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.acegisecurity.userdetails.UserDetails;

import ${package}.Constants;
import ${package}.security.service.SecurityUserManager;

public class UserAuthenticationProcessingFilter extends
		AuthenticationProcessingFilter {

	private SecurityUserManager userManager;

	public void setUserManager(SecurityUserManager userManager) {
		this.userManager = userManager;
	}

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		boolean requiresAuth = super.requiresAuthentication(request, response);
		if (!requiresAuth) {
			setUser(request, false);
		}
		return requiresAuth;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult)
			throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success: " + authResult.toString());
		}

		SecurityContextHolder.getContext().setAuthentication(authResult);

		setUser(request, true);

		if (logger.isDebugEnabled()) {
			logger
					.debug("Updated SecurityContextHolder to contain the following Authentication: '"
							+ authResult + "'");
		}

		String targetUrl = null;

		if (isAlwaysUseDefaultTargetUrl()) {
			targetUrl = request.getContextPath() + getDefaultTargetUrl();
		} else {
			String url = request
					.getParameter(Constants.REQUEST_PARAM_TARGET_URL);
			if (url == null || "".equals(url)) {
				targetUrl = obtainFullRequestUrl(request);
				if (targetUrl == null) {
					targetUrl = request.getContextPath()
							+ getDefaultTargetUrl();
				}
			} else {
				targetUrl = request.getContextPath() + url;
			}
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("Redirecting to target URL from HTTP Session (or default): "
							+ targetUrl);
		}

		onSuccessfulAuthentication(request, response, authResult);

		getRememberMeServices().loginSuccess(request, response, authResult);

		// Fire event
		if (this.eventPublisher != null) {
			eventPublisher
					.publishEvent(new InteractiveAuthenticationSuccessEvent(
							authResult, this.getClass()));
		}

		StringBuffer sb = new StringBuffer();

		sb
				.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		sb.append("<head>");
		sb
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		sb.append("<title></title>");
		sb.append("<meta name=\"jpt\" content=\"j_acegi_security_check\" />");
		sb.append("<meta http-equiv=\"Refresh\" Content=\"0;Url=");
		sb.append(targetUrl);
		sb.append("\">");
		sb.append("</head>");
		sb.append("<div>Loading...</div>");
		sb.append("<body>");
		sb.append("</body>");
		sb.append("</html>");

		response.getWriter().write(sb.toString());
		response.flushBuffer();
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException {
		SecurityContextHolder.getContext().setAuthentication(null);

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication request failed: " + failed.toString());
		}

		try {
			request.getSession().setAttribute(
					ACEGI_SECURITY_LAST_EXCEPTION_KEY, failed);
		} catch (Exception ignored) {
		}

		onUnsuccessfulAuthentication(request, response, failed);

		getRememberMeServices().loginFail(request, response);

		StringBuffer failureUrl = new StringBuffer();
		failureUrl.append(getExceptionMappings().getProperty(
				failed.getClass().getName(), getAuthenticationFailureUrl()));
		String extraParams = request
				.getParameter(Constants.REQUEST_PARAM_EXTRA_PARAMS);
		if (extraParams != null && !"".equals(extraParams)) {
			if (failureUrl.indexOf("?") >= 0) {
				failureUrl.append("&");
			} else {
				failureUrl.append("?");
			}
			failureUrl.append(extraParams);
		}

		sendRedirect(request, response, failureUrl.toString());
	}

	private void setUser(HttpServletRequest request, boolean replace) {
		HttpSession session = null;
		try {
			session = request.getSession(false);
		} catch (IllegalStateException ignored) {
		}
		if (session != null
				&& (replace || session.getAttribute(Constants.LOGIN_USER) == null)) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (auth != null) {
				Object principal = auth.getPrincipal();
				if (principal instanceof UserDetails) {
					Object user = userManager
							.getUserByName(((UserDetails) principal)
									.getUsername());
					session.setAttribute(Constants.LOGIN_USER, user);
				}
			}
		}
	}

}
