package ${package}.webapp.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import ${package}.service.Manager;

public class BaseAction<T extends Manager> extends ActionSupport {

	private static final long serialVersionUID = 4145041231380037321L;

	protected transient final Log logger = LogFactory.getLog(getClass());

	protected static final String MSG = "messages";

	protected T manager;

	protected Map params = new HashMap();

	protected Map root = new HashMap();

	protected Object rawdata;

	private Object onlyraw;

	private String _;

	public void setManager(T manager) {
		this.manager = manager;
	}

	public Object getRoot() {
		if (onlyraw != null) {
			return rawdata;
		}
		return root;
	}

	public Map getRootMap() {
		return root;
	}

	public Date getNow() {
		return new Date();
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public Object getOnlyraw() {
		return onlyraw;
	}

	public void setOnlyraw(Object onlyraw) {
		this.onlyraw = onlyraw;
	}

	/**
	 * 无实际意义，为了与ajax请求中的参数匹配，消除LOG信息。
	 * 
	 * @return
	 */
	public String get_() {
		return _;
	}

	public void set_(String _) {
		this._ = _;
	}

	public String getFieldText() {
		ValueStack stack = ActionContext.getContext().getValueStack();
		String fieldName = stack.findString("fieldName");
		if (fieldName == null || "".equals(fieldName)) {
			return "";
		}
		return getText(fieldName);
	}

	protected void clean() {
		root.clear();
		clearErrorsAndMessages();
	}

	protected void logRoot() {
		if (logger.isDebugEnabled()) {
			logger.debug("json root map:" + root);
		}
	}

	protected String getRealPath(String path) {
		return ServletActionContext.getServletContext().getRealPath(path);
	}

}
