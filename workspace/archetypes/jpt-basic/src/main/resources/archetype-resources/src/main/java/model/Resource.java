package ${package}.model;

import java.io.Serializable;

public class Resource extends BaseObject implements Serializable {

	private static final long serialVersionUID = -7698429918310531941L;

	private String name;
	
	private String url;
	
	private String descr;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDescr() {
		return descr;
	}
	
	public void setDescr(String descr) {
		this.descr = descr;
	}

}
