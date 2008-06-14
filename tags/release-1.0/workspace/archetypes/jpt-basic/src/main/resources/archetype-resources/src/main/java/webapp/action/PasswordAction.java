package ${package}.webapp.action;

import org.acegisecurity.providers.encoding.PasswordEncoder;

import ${package}.model.Password;
import ${package}.model.User;
import ${package}.service.UserManager;

public class PasswordAction<T extends UserManager> extends BasicAction<T> {

	private static final long serialVersionUID = -2252090701050441986L;

	private PasswordEncoder passwordEncoder;

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void validate() {
		clearErrorsAndMessages();
		Password password = (Password) model;
		User loginUser = (User) password.getLoginUser();
		String newPassword = passwordEncoder.encodePassword(password
				.getOrigPassword(), null);
		if (!newPassword.equals(loginUser.getPassword())) {
			addActionError(getText("errors.user.origpassmismatch"));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void prepareModel() {
		Password password = ((Password) model);
		String newPassword = passwordEncoder.encodePassword(password
				.getPassword(), null);
		password.setPassword(newPassword);
	}

}
