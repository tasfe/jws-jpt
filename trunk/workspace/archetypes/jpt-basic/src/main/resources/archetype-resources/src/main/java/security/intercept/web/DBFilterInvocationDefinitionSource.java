package ${package}.security.intercept.web;

import java.util.Iterator;
import java.util.Set;

import ${package}.security.ext.AuthorizeSupport;

import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.SecurityConfig;
import org.acegisecurity.intercept.web.AbstractFilterInvocationDefinitionSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBFilterInvocationDefinitionSource extends
		AbstractFilterInvocationDefinitionSource {

	protected final Log logger = LogFactory.getLog(getClass());

	private AuthorizeSupport authorizeSupport;

	public void setAuthorizeSupport(AuthorizeSupport authorizeSupport) {
		this.authorizeSupport = authorizeSupport;
	}

	public Iterator getConfigAttributeDefinitions() {
		return null;
	}

	@Override
	public ConfigAttributeDefinition lookupAttributes(String url) {

		Set<String> authorities = authorizeSupport.getRequiredAuthorities(url);

		if (authorities.size() > 0) {
			ConfigAttributeDefinition definition = new ConfigAttributeDefinition();
			for (String authority : authorities) {
				definition.addConfigAttribute(new SecurityConfig(authority));
			}
			return definition;
		}

		return null;
	}

}
