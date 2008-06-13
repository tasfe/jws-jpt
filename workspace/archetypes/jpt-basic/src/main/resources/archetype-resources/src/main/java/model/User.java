package ${package}.model;

import java.io.Serializable;
import java.util.List;

public class User extends BaseObject implements Serializable {

	private static final long serialVersionUID = 3832626162173359411L;

	private String name;

	private String password;

	private String confirmPassword;

	private boolean enabled = true;

	private String fullname;

	private String mobile;

	private String email;

	private List<Role> roles;

	private Long[] roleids;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Long[] getRoleids() {
		return roleids;
	}

	public void setRoleids(Long[] roleids) {
		this.roleids = roleids;
	}

}
