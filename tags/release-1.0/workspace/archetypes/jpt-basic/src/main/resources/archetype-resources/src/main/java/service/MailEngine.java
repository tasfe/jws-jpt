package ${package}.service;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;

public class MailEngine {
	protected static final Log logger = LogFactory.getLog(MailEngine.class);

	private MailSender mailSender;

	private Configuration freemarkerConfig;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setFreemarkerConfig(Configuration freemarkerConfig) {
		this.freemarkerConfig = freemarkerConfig;
	}

	public void sendMessage(String template, Object model,
			final String fromAddress, final String fromName,
			final String toAddress, final String toName, final String subject) {
		sendMessage(template, model, new MailEngineCallback() {
			public void setMail(MimeMessageHelper helper) throws Exception {
				helper.setFrom(fromAddress, fromName);
				helper.addTo(toAddress, toName);
				helper.setSubject(subject);
			}
		});
	}

	public void sendMessage(String template, Object model,
			MailEngineCallback action) {
		MimeMessage message = ((JavaMailSenderImpl) mailSender)
				.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			action.setMail(helper);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error(e.getMessage());
			}
		}
		if (template != null) {
			try {
				helper.setText(getMailText(template, model), true);
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.error(e.getMessage());
				}
			}
		}
		send(message);
	}

	public void send(MimeMessage msg) {
		try {
			((JavaMailSenderImpl) mailSender).send(msg);
		} catch (MailException e) {
			logger.error(e.getMessage());
		}
	}

	private String getMailText(String template, Object model) {
		try {
			return FreeMarkerTemplateUtils.processTemplateIntoString(
					freemarkerConfig.getTemplate(template), model);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error(e.getMessage());
			}
		}
		return null;
	}

}
