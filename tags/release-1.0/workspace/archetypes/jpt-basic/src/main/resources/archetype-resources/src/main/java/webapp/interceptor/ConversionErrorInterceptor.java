package ${package}.webapp.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.XWorkMessages;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.opensymphony.xwork2.util.ValueStack;

public class ConversionErrorInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 3523920371186971937L;

	public static final String ORIGINAL_PROPERTY_OVERRIDE = "original.property.override";

	protected Object getOverrideExpr(ActionInvocation invocation, Object value) {
		ValueStack stack = invocation.getStack();

		try {
			stack.push(value);

			return "'" + stack.findValue("top", String.class) + "'";
		} finally {
			stack.pop();
		}
	}

	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {

		ActionContext invocationContext = invocation.getInvocationContext();
		Map conversionErrors = invocationContext.getConversionErrors();
		ValueStack stack = invocationContext.getValueStack();

		HashMap fakie = null;

		for (Iterator iterator = conversionErrors.entrySet().iterator(); iterator
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String propertyName = (String) entry.getKey();
			Object value = entry.getValue();

			if (shouldAddError(propertyName, value)) {
				Object action = invocation.getAction();
				if (action instanceof ValidationAware) {
					ValidationAware va = (ValidationAware) action;
					va.addFieldError(propertyName, getErrorMessage(propertyName, stack));
				}

				if (fakie == null) {
					fakie = new HashMap();
				}

				fakie.put(propertyName, getOverrideExpr(invocation, value));
			}
		}

		if (fakie != null) {
			// if there were some errors, put the original (fake) values in
			// place right before the result
			stack.getContext().put(ORIGINAL_PROPERTY_OVERRIDE, fakie);
			invocation.addPreResultListener(new PreResultListener() {
				public void beforeResult(ActionInvocation invocation,
						String resultCode) {
					Map fakie = (Map) invocation.getInvocationContext().get(
							ORIGINAL_PROPERTY_OVERRIDE);

					if (fakie != null) {
						invocation.getStack().setExprOverrides(fakie);
					}
				}
			});
		}
		return invocation.invoke();
	}

	@SuppressWarnings("unchecked")
	private String getErrorMessage(String propertyName, ValueStack stack) {

		String defaultMessage = LocalizedTextUtil.findDefaultText(
				XWorkMessages.DEFAULT_INVALID_FIELDVALUE, ActionContext
						.getContext().getLocale(),
				new Object[] { propertyName });

		// 将形如 model.name 的属性转换成形如 fields.user.name，以便从国际化语言文件中取本地化语言
		if (propertyName.startsWith("model.")) {
			String modelName = stack
					.findString(BasicModelInterceptor.JPT_MODEL_NAME_IN_VALUE_STACK);
			if (modelName != null && !modelName.equals("")) {
				propertyName = "fields." + modelName
						+ propertyName.substring(5);
			}
		}

		Map context = new HashMap();
		context.put("fieldName", propertyName);
		try {
			stack.push(context);
			String message = stack
					.findString("getText('errors.model.invalid')");
			if (message != null) {
				return message;
			}
		} finally {
			stack.pop();
		}

		return defaultMessage;
	}

	protected boolean shouldAddError(String propertyName, Object value) {
		if (value == null) {
			return false;
		}

		if ("".equals(value)) {
			return false;
		}

		if (value instanceof String[]) {
			String[] array = (String[]) value;

			if (array.length == 0) {
				return false;
			}

			if (array.length > 1) {
				return true;
			}

			String str = array[0];

			if ("".equals(str)) {
				return false;
			}
		}

		return true;
	}

}