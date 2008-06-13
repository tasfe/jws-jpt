package ${package}.model;

import java.io.Serializable;

import com.googlecode.jsonplugin.annotations.JSON;

public class Menu extends BaseObject implements Serializable {

	private static final long serialVersionUID = 2609525258282639737L;

	private String name;

	private String code;

	private String target;

	private String url;

	private String suburl;

	private String img;

	private Long parentId;

	private Integer displayOrder;

	private Menu parent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSuburl() {
		return suburl;
	}

	public void setSuburl(String suburl) {
		this.suburl = suburl;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	@JSON(serialize = false)
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public String getParentName() {
		if (parent == null) {
			return null;
		}
		return parent.getName();
	}

}
