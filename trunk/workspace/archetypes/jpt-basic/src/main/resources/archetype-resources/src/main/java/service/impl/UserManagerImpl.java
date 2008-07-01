package ${package}.service.impl;

import java.util.List;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

import ${package}.model.User;
import ${package}.model.helper.SecurityHelper;
import ${package}.security.service.SecurityUserManager;
import ${package}.service.CacheManager;
import ${package}.service.ModelStatics;
import ${package}.service.UserManager;

public class UserManagerImpl extends BasicManagerImpl<User> implements
		UserManager, SecurityUserManager, ModelStatics {

	protected CacheManager cacheManager;

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public Object getUserByName(String username) {
		User user = (User) dao.get(M_USER_BY_NAME, username);
		return user;
	}

	public User getSimpleUserByName(String name) {
		return (User) dao.get(M_S_USER_BY_NAME, name);
	}

	public void removeUser(User user) {
		List origusers = dao.find(M_S_USERS_BY_ID, user);
		dao.remove(User.class, user);
		for (Object origuser : origusers) {
			cacheManager.updateUser(null, ((User) origuser).getName());
		}
	}

	public void updateUser(String name, User user) {
		User origuser = (User) dao.get(User.class, user.getId());
		dao.update(user);
		cacheManager.updateUser(user, origuser.getName());
	}

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		User user = (User) getUserByName(username);
		if (user == null) {
			throw new UsernameNotFoundException("user '" + username
					+ "' not found...");
		}
		return SecurityHelper.u2ud(user, SecurityHelper.p2ga(dao.find(
				M_PERMIS_BY_USER, user.getId())));
	}

}
