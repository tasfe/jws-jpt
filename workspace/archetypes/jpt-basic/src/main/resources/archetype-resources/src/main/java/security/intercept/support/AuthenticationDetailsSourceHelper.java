package ${package}.security.intercept.support;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.ui.AuthenticationDetailsSourceImpl;

public class AuthenticationDetailsSourceHelper extends
		AuthenticationDetailsSourceImpl {

	public Object buildDetails(HttpServletRequest request) {
		request.getSession();
		return super.buildDetails(request);
	}

}
