package ${package}.security.jcaptcha;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;

import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.intercept.web.FilterInvocation;
import org.acegisecurity.securechannel.ChannelEntryPoint;
import org.acegisecurity.securechannel.ChannelProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class ImageCaptchaChannelProcessor implements ChannelProcessor,
		InitializingBean {

	protected static final Log logger = LogFactory
			.getLog(ImageCaptchaChannelProcessor.class);

	private ChannelEntryPoint entryPoint;

	private String keyword = "REQUIRES_IMAGE_CAPTCHA_REQUESTS";

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(entryPoint, "entryPoint required");
		Assert.hasLength(keyword, "keyword required");
	}

	public void decide(FilterInvocation invocation,
			ConfigAttributeDefinition config) throws IOException,
			ServletException {
		if ((invocation == null) || (config == null)) {
			throw new IllegalArgumentException("Nulls cannot be provided");
		}

		ImageCaptchaSecurityContext context = (ImageCaptchaSecurityContext) SecurityContextHolder
				.getContext();

		Iterator iter = config.getConfigAttributes();

		while (iter.hasNext()) {
			ConfigAttribute attribute = (ConfigAttribute) iter.next();

			if (supports(attribute)) {
				logger.debug("supports this attribute : " + attribute);

				if (!context.isValid()) {
					logger
							.debug("context is not allowed to access ressource, redirect to captcha entry point");
					redirectToEntryPoint(invocation);
				} else {
					logger
							.debug("has been successfully checked this keyword, increment request count");
					context.incrementHumanRestrictedRessoucesRequestsCount();
				}
			} else {
				logger.debug("do not support this attribute");
			}
		}
	}

	public ChannelEntryPoint getEntryPoint() {
		return entryPoint;
	}

	public String getKeyword() {
		return keyword;
	}

	private void redirectToEntryPoint(FilterInvocation invocation)
			throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("context is not valid : redirecting to entry point");
		}

		entryPoint.commence(invocation.getRequest(), invocation.getResponse());
	}

	public void setEntryPoint(ChannelEntryPoint entryPoint) {
		this.entryPoint = entryPoint;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public boolean supports(ConfigAttribute attribute) {
		if ((attribute != null) && (keyword.equals(attribute.getAttribute()))) {
			return true;
		} else {
			return false;
		}
	}
}
