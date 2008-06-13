package ${package}.service;

import org.springframework.mail.javamail.MimeMessageHelper;

public interface MailEngineCallback {

	public void setMail(MimeMessageHelper helper) throws Exception;

}
