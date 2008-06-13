package ${package}.model;

import java.io.Serializable;
import java.util.List;

public class Role extends BaseObject implements Serializable {

	private static final long serialVersionUID = 3690197650654049848L;

	private String name;
	
	private String descr;
	
	private List<Permission> permissions;
	
	private Long[] permisids;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescr() {
		return descr;
	}
	
	public void setDescr(String descr) {
		this.descr = descr;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Long[] getPermisids() {
		return permisids;
	}

	public void setPermisids(Long[] permisids) {
		this.permisids = permisids;
	}

}
