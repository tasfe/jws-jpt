package ${package}.security.resourcedetails;

import java.io.Serializable;

import org.acegisecurity.GrantedAuthority;

public interface ResourceDetails extends Serializable {

	public String getUrl();

	public GrantedAuthority[] getAuthorities();

}
