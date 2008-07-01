package ${package}.model;

import java.io.Serializable;
import java.util.List;

public class Dictionary extends BaseObject implements Serializable {

	private static final long serialVersionUID = 2926859351368622610L;

	private String code;

	private String name;

	private String asscNames;

	private String text;

	private String descr;

	private Integer value;

	private Integer layer;

	private Integer displayOrder;

	private Long parentId;

	private List<Dictionary> subdicts;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAsscNames() {
		return asscNames;
	}

	public void setAsscNames(String asscNames) {
		this.asscNames = asscNames;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<Dictionary> getSubdicts() {
		return subdicts;
	}

	public void setSubdicts(List<Dictionary> subdicts) {
		this.subdicts = subdicts;
	}

}
