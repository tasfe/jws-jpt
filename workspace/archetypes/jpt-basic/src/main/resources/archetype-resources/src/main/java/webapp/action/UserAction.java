package ${package}.webapp.action;

import org.acegisecurity.providers.encoding.PasswordEncoder;

import ${package}.model.User;
import ${package}.service.UserManager;

public class UserAction<T extends UserManager> extends BasicAction<T> {

	private static final long serialVersionUID = 1008732830639211919L;

	private boolean encryptPass;

	private PasswordEncoder passwordEncoder;

	public boolean isEncryptPass() {
		return encryptPass;
	}

	public void setEncryptPass(boolean encryptPass) {
		this.encryptPass = encryptPass;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void validate() {
		clearErrorsAndMessages();
		User user = (User) model;
		User origUser = manager.getSimpleUserByName(user.getName());
		if (origUser != null && !origUser.getId().equals(user.getId())) {
			addActionError(getText("errors.user.nonunique",
					"username nonunique.", user.getName()));
			if (logger.isDebugEnabled()) {
				logger.debug("username nonunique.");
			}
		}
	}

	@Override
	protected void prepareModel() {
		User user = (User) model;
		if (user.isNewObj() || encryptPass) {
			user.setPassword(passwordEncoder.encodePassword(user.getPassword(),
					null));
		}
	}

}
