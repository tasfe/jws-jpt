package ${package}.security.service;

import org.acegisecurity.userdetails.UserDetailsService;

public interface SecurityUserManager extends UserDetailsService {

	public Object getUserByName(String username);

}