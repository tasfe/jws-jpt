package ${package}.security.jcaptcha;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.securechannel.ChannelEntryPoint;
import org.acegisecurity.util.PortMapper;
import org.acegisecurity.util.PortMapperImpl;
import org.acegisecurity.util.PortResolver;
import org.acegisecurity.util.PortResolverImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class ImageCaptchaEntryPoint implements ChannelEntryPoint,
		InitializingBean {

	private static final Log logger = LogFactory
			.getLog(ImageCaptchaEntryPoint.class);

	private PortMapper portMapper = new PortMapperImpl();

	private PortResolver portResolver = new PortResolverImpl();

	private String captchaFormUrl;

	private String originalRequestMethodParameterName = "original_request_method";

	private String originalRequestParametersNameValueSeparator = "__";

	private String originalRequestParametersParameterName = "original_request_parameters";

	private String originalRequestParametersSeparator = ";;";

	private String originalRequestUrlParameterName = "original_requestUrl";

	private String urlEncodingCharset = "UTF-8";

	private boolean forceHttps = false;

	private boolean includeOriginalParameters = false;

	private boolean includeOriginalRequest = true;

	private boolean isOutsideWebApp = false;

	private boolean forceForward = false;

	private String parameter = "captchaFormUrl";

	public void afterPropertiesSet() throws Exception {
		Assert.hasLength(parameter, "parameter must be specified");
		Assert.hasLength(originalRequestMethodParameterName,
				"originalRequestMethodParameterName must be specified");
		Assert
				.hasLength(originalRequestParametersNameValueSeparator,
						"originalRequestParametersNameValueSeparator must be specified");
		Assert.hasLength(originalRequestParametersParameterName,
				"originalRequestParametersParameterName must be specified");
		Assert.hasLength(originalRequestParametersSeparator,
				"originalRequestParametersSeparator must be specified");
		Assert.hasLength(originalRequestUrlParameterName,
				"originalRequestUrlParameterName must be specified");
		Assert.hasLength(urlEncodingCharset,
				"urlEncodingCharset must be specified");
		Assert.notNull(portMapper, "portMapper must be specified");
		Assert.notNull(portResolver, "portResolver must be specified");
		URLEncoder.encode("   fzaef �& � ", urlEncodingCharset);
	}

	private void buildInternalRedirect(StringBuffer redirectUrl,
			HttpServletRequest req) {

		StringBuffer simpleRedirect = new StringBuffer();

		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = portResolver.getServerPort(req);
		String contextPath = req.getContextPath();
		boolean includePort = true;

		if ("http".equals(scheme.toLowerCase()) && (serverPort == 80)) {
			includePort = false;
		}

		if ("https".equals(scheme.toLowerCase()) && (serverPort == 443)) {
			includePort = false;
		}

		simpleRedirect.append(scheme);
		simpleRedirect.append("://");
		simpleRedirect.append(serverName);

		if (includePort) {
			simpleRedirect.append(":");
			simpleRedirect.append(serverPort);
		}

		simpleRedirect.append(contextPath);
		simpleRedirect.append(obtainCaptchaFormUrl(req));

		if (forceHttps && req.getScheme().equals("http")) {
			Integer httpPort = new Integer(portResolver.getServerPort(req));
			Integer httpsPort = (Integer) portMapper.lookupHttpsPort(httpPort);

			if (httpsPort != null) {
				if (httpsPort.intValue() == 443) {
					includePort = false;
				} else {
					includePort = true;
				}

				redirectUrl.append("https://");
				redirectUrl.append(serverName);

				if (includePort) {
					redirectUrl.append(":");
					redirectUrl.append(httpsPort);
				}

				redirectUrl.append(contextPath);
				redirectUrl.append(obtainCaptchaFormUrl(req));
			} else {
				redirectUrl.append(simpleRedirect);
			}
		} else {
			redirectUrl.append(simpleRedirect);
		}
	}

	public void commence(ServletRequest servletRequest,
			ServletResponse servletResponse) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String url = obtainCaptchaFormUrl(request);

		if (url == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Bad captchaFormUrl!");
			}
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"page not found!");
			return;
		}

		if (forceForward) {
			RequestDispatcher rd = request.getRequestDispatcher(url);
			if (rd != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Forwarding to: " + url);
				}
				rd.forward(servletRequest, servletResponse);

				return;
			}
		}

		StringBuffer redirectUrl = new StringBuffer();

		if (isOutsideWebApp) {
			redirectUrl = redirectUrl.append(url);
		} else {
			buildInternalRedirect(redirectUrl, request);
		}

		if (includeOriginalRequest) {
			includeOriginalRequest(redirectUrl, request);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Redirecting to: " + redirectUrl);
		}

		response.sendRedirect(redirectUrl.toString());
	}

	public String obtainCaptchaFormUrl(HttpServletRequest request) {
		String value = request.getParameter(parameter);
		if (value != null && !"".equals(value)) {
			return value;
		}
		return captchaFormUrl;
	}

	public String getCaptchaFormUrl() {
		return captchaFormUrl;
	}

	public boolean getForceHttps() {
		return forceHttps;
	}

	public String getOriginalRequestMethodParameterName() {
		return originalRequestMethodParameterName;
	}

	public String getOriginalRequestParametersNameValueSeparator() {
		return originalRequestParametersNameValueSeparator;
	}

	public String getOriginalRequestParametersParameterName() {
		return originalRequestParametersParameterName;
	}

	public String getOriginalRequestParametersSeparator() {
		return originalRequestParametersSeparator;
	}

	public String getOriginalRequestUrlParameterName() {
		return originalRequestUrlParameterName;
	}

	public PortMapper getPortMapper() {
		return portMapper;
	}

	public PortResolver getPortResolver() {
		return portResolver;
	}

	public String getUrlEncodingCharset() {
		return urlEncodingCharset;
	}

	private void includeOriginalRequest(StringBuffer redirectUrl,
			HttpServletRequest req) {

		if (redirectUrl.indexOf("?") >= 0) {
			redirectUrl.append("&");
		} else {
			redirectUrl.append("?");
		}

		redirectUrl.append(originalRequestUrlParameterName);
		redirectUrl.append("=");

		try {
			redirectUrl.append(URLEncoder.encode(
					req.getRequestURL().toString(), urlEncodingCharset));
		} catch (UnsupportedEncodingException e) {
			logger.warn(e);
		}

		redirectUrl.append("&");
		redirectUrl.append(originalRequestMethodParameterName);
		redirectUrl.append("=");
		redirectUrl.append(req.getMethod());

		if (includeOriginalParameters) {

			redirectUrl.append("&");
			redirectUrl.append(originalRequestParametersParameterName);
			redirectUrl.append("=");

			StringBuffer qp = new StringBuffer();
			Enumeration parameters = req.getParameterNames();

			if ((parameters != null) && parameters.hasMoreElements()) {

				while (parameters.hasMoreElements()) {
					String name = parameters.nextElement().toString();
					String value = req.getParameter(name);
					qp.append(name);
					qp.append(originalRequestParametersNameValueSeparator);
					qp.append(value);

					if (parameters.hasMoreElements()) {
						qp.append(originalRequestParametersSeparator);
					}
				}
			}

			try {
				redirectUrl.append(URLEncoder.encode(qp.toString(),
						urlEncodingCharset));
			} catch (Exception e) {
				logger.warn(e);
			}
		}
	}

	public boolean isIncludeOriginalParameters() {
		return includeOriginalParameters;
	}

	public boolean isIncludeOriginalRequest() {
		return includeOriginalRequest;
	}

	public boolean isOutsideWebApp() {
		return isOutsideWebApp;
	}

	public void setCaptchaFormUrl(String captchaFormUrl) {
		this.captchaFormUrl = captchaFormUrl;
	}

	public void setForceHttps(boolean forceHttps) {
		this.forceHttps = forceHttps;
	}

	public void setIncludeOriginalParameters(boolean includeOriginalParameters) {
		this.includeOriginalParameters = includeOriginalParameters;
	}

	public void setIncludeOriginalRequest(boolean includeOriginalRequest) {
		this.includeOriginalRequest = includeOriginalRequest;
	}

	public void setOriginalRequestMethodParameterName(
			String originalRequestMethodParameterName) {
		this.originalRequestMethodParameterName = originalRequestMethodParameterName;
	}

	public void setOriginalRequestParametersNameValueSeparator(
			String originalRequestParametersNameValueSeparator) {
		this.originalRequestParametersNameValueSeparator = originalRequestParametersNameValueSeparator;
	}

	public void setOriginalRequestParametersParameterName(
			String originalRequestParametersParameterName) {
		this.originalRequestParametersParameterName = originalRequestParametersParameterName;
	}

	public void setOriginalRequestParametersSeparator(
			String originalRequestParametersSeparator) {
		this.originalRequestParametersSeparator = originalRequestParametersSeparator;
	}

	public void setOriginalRequestUrlParameterName(
			String originalRequestUrlParameterName) {
		this.originalRequestUrlParameterName = originalRequestUrlParameterName;
	}

	public void setOutsideWebApp(boolean isOutsideWebApp) {
		this.isOutsideWebApp = isOutsideWebApp;
	}

	public void setPortMapper(PortMapper portMapper) {
		this.portMapper = portMapper;
	}

	public void setPortResolver(PortResolver portResolver) {
		this.portResolver = portResolver;
	}

	public void setUrlEncodingCharset(String urlEncodingCharset) {
		this.urlEncodingCharset = urlEncodingCharset;
	}

	public boolean isForceForward() {
		return forceForward;
	}

	public String getParameter() {
		return parameter;
	}

	public void setForceForward(boolean forceForward) {
		this.forceForward = forceForward;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
