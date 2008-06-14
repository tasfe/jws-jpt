package ${package}.service.impl;

import ${package}.model.Password;
import ${package}.model.User;
import ${package}.service.CacheManager;
import ${package}.service.ModelStatics;

public class PasswordManagerImpl extends BasicManagerImpl<Password> implements
		ModelStatics {

	protected CacheManager cacheManager;

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void updatePassword(String name, Password password) {
		dao.update(name, password);
		User user = (User) password.getLoginUser();
		user.setPassword(password.getPassword());
		cacheManager.updateUser(user, user.getName());
	}

}
