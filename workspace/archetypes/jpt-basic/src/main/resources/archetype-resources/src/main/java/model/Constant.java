package ${package}.model;

import java.io.Serializable;

public class Constant extends BaseObject implements Serializable {

	private static final long serialVersionUID = 2564276755158443201L;

	private String name;

	private Double value;

	private String descr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
}
