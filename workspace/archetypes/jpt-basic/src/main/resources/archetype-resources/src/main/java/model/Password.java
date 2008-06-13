package ${package}.model;

import java.io.Serializable;

public class Password extends BaseObject implements Serializable {

	private static final long serialVersionUID = -2744142427702746331L;

	private String password;

	private String confirmPassword;

	private String origPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getOrigPassword() {
		return origPassword;
	}

	public void setOrigPassword(String origPassword) {
		this.origPassword = origPassword;
	}

}
