package ${package}.service;

import ${package}.model.User;

public interface UserManager extends Manager {

	public User getSimpleUserByName(String name);

}
