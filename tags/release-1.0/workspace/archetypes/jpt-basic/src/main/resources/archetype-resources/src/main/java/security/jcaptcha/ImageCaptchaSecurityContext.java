package ${package}.security.jcaptcha;

import org.acegisecurity.captcha.CaptchaSecurityContext;

public interface ImageCaptchaSecurityContext extends CaptchaSecurityContext {

	public boolean isValid();

	public void setValid(boolean valid);
}
