package ${package}.webapp;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsConstants;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.spring.SpringObjectFactory;

public class StrutsSpringObjectFactory extends SpringObjectFactory {

	private static final long serialVersionUID = 230868309416212214L;

	private static final Log logger = LogFactory
			.getLog(StrutsSpringObjectFactory.class);

	private String actionPackageName = "${package}.webapp.action";

	private String managerPackageName = "${package}.service.impl";

	private String actionBeanPostfix = "JptAction";

	private String actionClassNamePostfix = "Action";

	private String managerClassNamePostfix = "ManagerImpl";

	private String basicManager = "manager";

	private String basicDao = "dao";

	private String managerProperty = "manager";

	private String daoProperty = "dao";

	public String getActionPackageName() {
		return actionPackageName;
	}

	public void setActionPackageName(String actionPackageName) {
		this.actionPackageName = actionPackageName;
	}

	public String getManagerPackageName() {
		return managerPackageName;
	}

	public void setManagerPackageName(String managerPackageName) {
		this.managerPackageName = managerPackageName;
	}

	public String getActionBeanPostfix() {
		return actionBeanPostfix;
	}

	public void setActionBeanPostfix(String actionBeanPostfix) {
		this.actionBeanPostfix = actionBeanPostfix;
	}

	public String getActionClassNamePostfix() {
		return actionClassNamePostfix;
	}

	public void setActionClassNamePostfix(String actionClassNamePostfix) {
		this.actionClassNamePostfix = actionClassNamePostfix;
	}

	public String getManagerClassNamePostfix() {
		return managerClassNamePostfix;
	}

	public void setManagerClassNamePostfix(String managerClassNamePostfix) {
		this.managerClassNamePostfix = managerClassNamePostfix;
	}

	@Inject
	public StrutsSpringObjectFactory(
			@Inject(value = StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_AUTOWIRE, required = false) String autoWire,
			@Inject(value = StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_USE_CLASS_CACHE, required = false) String useClassCacheStr,
			@Inject ServletContext servletContext) {

		super();
		boolean useClassCache = "true".equals(useClassCacheStr);
		logger.info("Initializing Struts-Spring integration...");

		ApplicationContext appContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		if (appContext == null) {
			// uh oh! looks like the lifecycle listener wasn't installed. Let's
			// inform the user
			String message = "********** FATAL ERROR STARTING UP STRUTS-SPRING INTEGRATION **********\n"
					+ "Looks like the Spring listener was not configured for your web app! \n"
					+ "Nothing will work until WebApplicationContextUtils returns a valid ApplicationContext.\n"
					+ "You might need to add the following to web.xml: \n"
					+ "    <listener>\n"
					+ "        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>\n"
					+ "    </listener>";
			logger.fatal(message);
			return;
		}

		this.setApplicationContext(appContext);

		int type = AutowireCapableBeanFactory.AUTOWIRE_BY_NAME; // default
		if ("name".equals(autoWire)) {
			type = AutowireCapableBeanFactory.AUTOWIRE_BY_NAME;
		} else if ("type".equals(autoWire)) {
			type = AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE;
		} else if ("auto".equals(autoWire)) {
			type = AutowireCapableBeanFactory.AUTOWIRE_AUTODETECT;
		} else if ("constructor".equals(autoWire)) {
			type = AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR;
		}
		this.setAutowireStrategy(type);

		this.setUseClassCache(useClassCache);

		logger.info("... initialized Struts-Spring integration successfully");
	}

	@Override
	public Object buildAction(String actionName, String namespace,
			ActionConfig config, Map extraContext) throws Exception {
		String beanName = config.getClassName();
		String actionType = null;
		int i = actionName.indexOf("/");
		if (i != -1) {
			actionType = actionName.substring(0, i);
		}
		if (!appContext.containsBean(beanName)) {
			String modelName = getModelName(beanName);
			if (modelName != null) {
				ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) appContext;
				DefaultListableBeanFactory factory = (DefaultListableBeanFactory) ctx
						.getBeanFactory();
				Class actionClass = getActionClass(modelName);
				String managerBeanName = getManagerBeanName(modelName);

				if (!ctx.containsBean(managerBeanName)) {
					Class managerClass = getManagerClass(modelName);
					if (managerClass != null) {
						RootBeanDefinition rbd = new RootBeanDefinition();
						rbd.setBeanClass(managerClass);
						rbd.getPropertyValues().addPropertyValue(daoProperty,
								ctx.getBean(basicDao));
						rbd
								.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
						factory.registerBeanDefinition(managerBeanName, rbd);						
					} else if (actionClass != null) {
						managerBeanName = basicManager;
					}
					if (actionClass == null) {
						beanName = actionType + actionBeanPostfix;
					}

				}

				if (!ctx.containsBean(beanName)) {
					RootBeanDefinition rbd = new RootBeanDefinition();
					rbd.setBeanClass(actionClass);
					rbd.setScope(BeanDefinition.SCOPE_PROTOTYPE);
					rbd.getPropertyValues().addPropertyValue(managerProperty,
							ctx.getBean(managerBeanName));
					rbd
							.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
					factory.registerBeanDefinition(beanName, rbd);
				}
			}
		}
		return super.buildBean(beanName, extraContext, true);
	}

	private String getModelName(String actionBeanName) {
		if (!actionBeanName.endsWith(actionBeanPostfix)) {
			return null;
		}
		return actionBeanName.substring(0, actionBeanName.length()
				- actionBeanPostfix.length());
	}

	private String getManagerBeanName(String modelName) {
		return modelName + "Manager";
	}

	private Class getActionClass(String modelName) {
		try {
			String className = actionPackageName + "."
					+ StringUtils.capitalize(modelName)
					+ actionClassNamePostfix;
			return ClassUtils.forName(className);
		} catch (Exception e) {
		}
		return null;
	}

	private Class getManagerClass(String modelName) {
		try {
			String className = managerPackageName + "."
					+ StringUtils.capitalize(modelName)
					+ managerClassNamePostfix;
			return ClassUtils.forName(className);
		} catch (Exception e) {
		}
		return null;
	}
}
