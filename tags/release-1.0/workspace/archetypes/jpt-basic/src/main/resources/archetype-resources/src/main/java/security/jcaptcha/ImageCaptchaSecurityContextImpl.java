package ${package}.security.jcaptcha;

import org.acegisecurity.captcha.CaptchaSecurityContextImpl;

public class ImageCaptchaSecurityContextImpl extends CaptchaSecurityContextImpl
		implements ImageCaptchaSecurityContext {

	private static final long serialVersionUID = 3832466162178359419L;

	boolean valid;

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;

	}

}
