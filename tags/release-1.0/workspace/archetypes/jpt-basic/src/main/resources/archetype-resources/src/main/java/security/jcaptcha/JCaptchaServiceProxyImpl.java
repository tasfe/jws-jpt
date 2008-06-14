package ${package}.security.jcaptcha;

import org.acegisecurity.captcha.CaptchaServiceProxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

public class JCaptchaServiceProxyImpl implements CaptchaServiceProxy {

	protected static final Log logger = LogFactory
			.getLog(JCaptchaServiceProxyImpl.class);

	private CaptchaService jcaptchaService;

	public boolean validateReponseForId(String id, Object response) {
		logger.debug("validating captcha response");

		try {
			boolean isHuman = jcaptchaService.validateResponseForID(id,
					response).booleanValue();
			if (isHuman) {
				logger.debug("captcha passed");
			} else {
				logger.warn("captcha failed");
			}
			return isHuman;

		} catch (CaptchaServiceException cse) {
			logger.warn("captcha validation failed due to exception", cse);
			return false;
		}
	}

	public void setJcaptchaService(CaptchaService jcaptchaService) {
		this.jcaptchaService = jcaptchaService;
	}
}
