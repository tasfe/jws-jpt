package ${package}.webapp.interceptor;

import ${package}.Constants;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginUserInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1911229921895128173L;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		Object loginUser = ServletActionContext.getRequest().getSession()
				.getAttribute(Constants.LOGIN_USER);
		invocation.getInvocationContext().getValueStack().setValue(
				Constants.LOGIN_USER, loginUser);

		return invocation.invoke();
	}

}
