package ${package}.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.googlecode.jsonplugin.annotations.JSON;
import ${package}.Constants;

public abstract class BaseObject implements Serializable {

	private static final long serialVersionUID = 6429163667604344087L;

	protected Map ctx = new HashMap();

	protected Long id;

	protected Long[] chkIDs;

	@JSON(serialize = false)
	public Map getCtx() {
		return ctx;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JSON(serialize = false)
	public Long[] getChkIDs() {
		return chkIDs;
	}

	public void setChkIDs(Long[] chkIDs) {
		this.chkIDs = chkIDs;
	}

	@JSON(serialize = false)
	public boolean isNewObj() {
		return id == null;
	}

	@JSON(serialize = false)
	public Object getLoginUser() {
		return ctx.get(Constants.LOGIN_USER);
	}

	@JSON(serialize = false)
	public Object getP0() {
		return this;
	}

	@SuppressWarnings("unchecked")
	public void setLoginUser(Object loginUser) {
		ctx.put(Constants.LOGIN_USER, loginUser);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (!getClass().equals(o.getClass())) {
			return false;
		}

		final BaseObject obj = (BaseObject) o;

		if (id != null) {
			return id.equals(obj.id);
		}
		return obj.id == null;
	}

	@Override
	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("id", id);
		return sb.toString();
	}
}
