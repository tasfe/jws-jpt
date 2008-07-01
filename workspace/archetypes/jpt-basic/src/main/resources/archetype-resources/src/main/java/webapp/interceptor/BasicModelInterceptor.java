package ${package}.webapp.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import ${package}.Constants;
import ${package}.model.helper.ModelHelper;

public class BasicModelInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -3527605131504990882L;

	public static final String JPT_MODEL_NAME_IN_REQUEST = "JPT_MODEL_NAME";

	public static final String JPT_MODEL_PREFIX_IN_REQUEST = "JPT_MODEL_PREFIX";

	public static final String JPT_MODEL_POSTFIX_IN_REQUEST = "JPT_MODEL_POSTFIX";

	public static final String JPT_MODEL_IN_VALUE_STACK = "model";

	public static final String JPT_FILTERS_IN_VALUE_STACK = "filters";

	public static final String JPT_SORTERS_IN_VALUE_STACK = "sorters";

	public static final String JPT_PARAMS_IN_VALUE_STACK = "params";

	public static final String JPT_MODEL_NAME_IN_VALUE_STACK = "modelName";

	public static final String JPT_NAME_IN_VALUE_STACK = "name";

	protected transient final Log logger = LogFactory.getLog(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com
	 * .opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		ActionContext ac = invocation.getInvocationContext();
		Map parameters = ac.getParameters();

		String modelName = (String) request
				.getAttribute(JPT_MODEL_NAME_IN_REQUEST);
		String modelPrefix = (String) request
				.getAttribute(JPT_MODEL_PREFIX_IN_REQUEST);
		String modelPostfix = (String) request
				.getAttribute(JPT_MODEL_POSTFIX_IN_REQUEST);

		Object loginUser = session.getAttribute(Constants.LOGIN_USER);

		if (!StringUtils.isEmpty(modelName)) {
			String name = "";
			if (!StringUtils.isEmpty(modelPrefix)) {
				name = modelPrefix;
			}
			name += StringUtils.capitalize(modelName);
			if (!StringUtils.isEmpty(modelPostfix)) {
				if (modelPostfix.matches("^s\\d*")) {
					name += modelPostfix;
				} else {
					name += StringUtils.capitalize(modelPostfix);
				}
			}

			Object model = ModelHelper.getModel(modelName, null);
			Object filters = ModelHelper.getModel(modelName, "Filters");
			ModelHelper.setLoginUser(model, loginUser);
			ModelHelper.setLoginUser(filters, loginUser);

			Map sorters = getParameters("sorters", parameters);
			Map params = getParameters("params", parameters);

			addParameters("params", params, request, true);
			addParameters("extra", parameters, request, true);
			addParameters("model", parameters, request, false);

			ValueStack stack = ac.getValueStack();

			push(JPT_MODEL_IN_VALUE_STACK, model, stack);
			push(JPT_FILTERS_IN_VALUE_STACK, filters, stack);
			push(JPT_SORTERS_IN_VALUE_STACK, sorters, stack);
			push(JPT_PARAMS_IN_VALUE_STACK, params, stack);
			push(JPT_MODEL_NAME_IN_VALUE_STACK, modelName, stack);
			push(JPT_NAME_IN_VALUE_STACK, name, stack);
			push("fields." + modelName, model, stack);

		}
		return invocation.invoke();
	}

	private void push(String name, Object value, ValueStack stack) {
		try {
			stack.setValue(name, value);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage(), e.getCause());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Map getParameters(String prefix, final Map parameters) {
		Map params = new HashMap();
		for (Object o : parameters.entrySet()) {
			Map.Entry entry = (Map.Entry) o;
			String key = (String) entry.getKey();
			if (key.startsWith(prefix + ".")) {
				String name = key.substring(prefix.length() + 1);
				params.put(name, entry.getValue());
			}
		}
		return params;
	}

	@SuppressWarnings("unchecked")
	private Map addParameters(String prefix, Map parameters,
			HttpServletRequest request, boolean flat) {
		Enumeration attrs = request.getAttributeNames();
		while (attrs.hasMoreElements()) {
			String attr = (String) attrs.nextElement();
			if (attr.startsWith(prefix + ".")) {
				String name = null;
				if (flat) {
					name = attr.substring(prefix.length() + 1);
				} else {
					name = attr;
				}
				parameters.put(name, request.getAttribute(attr));
			}
		}
		return parameters;
	}

}
