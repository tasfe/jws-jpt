package ${package}.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.acegisecurity.captcha.CaptchaServiceProxy;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import ${package}.security.jcaptcha.ImageCaptchaSecurityContext;

public class ImageCaptchaValidationProcessingFilter implements
		InitializingBean, Filter {

	protected static final Log logger = LogFactory
			.getLog(ImageCaptchaValidationProcessingFilter.class);

	private CaptchaServiceProxy captchaService;

	private String captchaValidationParameter = "_captcha_parameter";

	public void afterPropertiesSet() throws Exception {
		if (this.captchaService == null) {
			throw new IllegalArgumentException(
					"CaptchaServiceProxy must be defined ");
		}

		if ((this.captchaValidationParameter == null)
				|| "".equals(captchaValidationParameter)) {
			throw new IllegalArgumentException(
					"captchaValidationParameter must not be empty or null");
		}
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String captcha_reponse = request
				.getParameter(captchaValidationParameter);

		if ((request != null) && request instanceof HttpServletRequest
				&& (captcha_reponse != null)) {
			logger.debug("captcha validation parameter found");

			logger.debug("try to validate");

			// get session
			HttpSession session = ((HttpServletRequest) request).getSession();

			if (session != null) {
				String id = session.getId();
				boolean valid = this.captchaService.validateReponseForId(id,
						captcha_reponse);
				logger.debug("captchaServiceProxy says : request is valid = "
						+ valid);
				ImageCaptchaSecurityContext context = (ImageCaptchaSecurityContext) SecurityContextHolder
						.getContext();
				context.setValid(valid);
				if (valid) {
					logger.debug("update the context");
					context.setHuman();
				} else {
					logger.debug("captcha test failed");
				}
			} else {
				logger
						.debug("no session found, user don't even ask a captcha challenge");
			}
		} else {
			logger.debug(((HttpServletRequest) request).getRequestURI() + ":");
			logger.debug("captcha validation parameter not found, do nothing");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("chain ...");
		}

		chain.doFilter(request, response);
	}

	public CaptchaServiceProxy getCaptchaService() {
		return captchaService;
	}

	public String getCaptchaValidationParameter() {
		return captchaValidationParameter;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void setCaptchaService(CaptchaServiceProxy captchaService) {
		this.captchaService = captchaService;
	}

	public void setCaptchaValidationParameter(String captchaValidationParameter) {
		this.captchaValidationParameter = captchaValidationParameter;
	}
}
