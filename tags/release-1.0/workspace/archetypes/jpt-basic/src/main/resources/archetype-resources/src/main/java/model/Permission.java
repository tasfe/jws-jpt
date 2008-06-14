package ${package}.model;

import java.io.Serializable;
import java.util.List;

public class Permission extends BaseObject implements Serializable {

	private static final long serialVersionUID = -7796075030761614571L;

	private String name;

	private String authority;

	private String descr;

	private List<Resource> resources;

	private List<Menu> menus;

	private Long[] rescids;

	private Long[] menuids;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Long[] getRescids() {
		return rescids;
	}

	public void setRescids(Long[] rescids) {
		this.rescids = rescids;
	}

	public Long[] getMenuids() {
		return menuids;
	}

	public void setMenuids(Long[] menuids) {
		this.menuids = menuids;
	}

}
