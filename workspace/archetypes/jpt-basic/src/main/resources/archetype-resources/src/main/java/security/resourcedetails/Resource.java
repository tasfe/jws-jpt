package ${package}.security.resourcedetails;

import org.acegisecurity.GrantedAuthority;

public class Resource implements ResourceDetails {

	private static final long serialVersionUID = 6376595428653867012L;

	private String url;

	private GrantedAuthority[] authorities;

	public Resource(String url, GrantedAuthority[] authorities) {
		setUrl(url);
		setAuthorities(authorities);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public GrantedAuthority[] getAuthorities() {
		return authorities;
	}

	protected void setAuthorities(GrantedAuthority[] authorities) {
		this.authorities = authorities;
	}

	public boolean equals(Object rhs) {
		if (rhs == null || !(rhs instanceof Resource))
			return false;
		Resource resauth = (Resource) rhs;
		if (resauth.getAuthorities().length != getAuthorities().length)
			return false;
		for (int i = 0; i < getAuthorities().length; i++) {
			if (!getAuthorities()[i].equals(resauth.getAuthorities()[i]))
				return false;
		}
		return url.equals(resauth.getUrl());
	}

	public int hashCode() {
		int code = 168;
		if (getAuthorities() != null) {
			for (int i = 0; i < getAuthorities().length; i++)
				code *= getAuthorities()[i].hashCode() % 7;
		}
		if (url != null)
			code *= url.hashCode() % 7;
		return code;
	}

}
