package ${package}.service.impl;

import java.util.Collection;
import java.util.Map;

import ${package}.model.Permission;
import ${package}.model.Resource;
import ${package}.model.User;
import ${package}.security.resourcedetails.ResourceDetails;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.UserDetails;

public class ConvertUtil {

	public static UserDetails u2ud(User user, GrantedAuthority[] authorities) {
		return new org.acegisecurity.userdetails.User(user.getName(), user
				.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

	public static ResourceDetails r2rd(Resource resource,
			GrantedAuthority[] authorities) {
		return new ${package}.security.resourcedetails.Resource(resource.getUrl(),
				authorities);
	}

	public static GrantedAuthority[] p2ga(Collection permissions) {
		GrantedAuthority[] authorities = new GrantedAuthority[permissions
				.size()];
		int i = 0;
		for (Object permission : permissions) {
			if (permission instanceof Permission) {
				authorities[i] = new GrantedAuthorityImpl(
						((Permission) permission).getAuthority());
			} else if (permission instanceof Map) {
				authorities[i] = new GrantedAuthorityImpl(((Map) permission)
						.get("permission").toString());
			} else {
				authorities[i] = new GrantedAuthorityImpl(permission.toString());
			}
			i++;
		}

		return authorities;
	}

}
