package ${package}.webapp.action;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

import ${package}.model.User;
import ${package}.model.helper.ModelHelper;

public class DefaultAction extends BaseAction {

	private static final long serialVersionUID = -7691174182714157982L;

	private User loginUser;

	private String name;

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
		if (this.loginUser != null) {
			this.loginUser.setLoginUser(loginUser);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	public String list() throws Exception {

		clean();

		String[] dependency = ModelHelper.getModelDependency(StringUtils
				.uncapitalize(name)
				+ ".r");
		if (dependency != null) {
			writeDependency(dependency);

		} else {
			root.put(name, new ArrayList(manager.find(StringUtils
					.uncapitalize(name), loginUser)));
			dependency = ModelHelper.getModelDependency(StringUtils
					.uncapitalize(name));
			if (dependency != null) {
				writeDependency(dependency);

			}
		}

		logRoot();

		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private void writeDependency(String[] dependency) throws Exception {
		for (String depName : dependency) {
			Object depObj = new ArrayList(manager.find(depName, loginUser));
			if (depObj != null) {
				root.put(depName, depObj);
			} else {
				root.put(depName, Collections.EMPTY_LIST);
			}
		}
	}

}
