package ${package}.webapp.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;

public class StartupListener implements ServletContextListener {

	private static final Log logger = LogFactory.getLog(StartupListener.class);

	public void contextInitialized(ServletContextEvent event) {

		if (logger.isDebugEnabled()) {
			logger.debug("initializing context...");
		}

		ServletContext context = event.getServletContext();

		try {
			TemplateHashModel staticModels = BeansWrapper.getDefaultInstance()
					.getStaticModels();
			context.setAttribute("Constants", staticModels
					.get("${package}.Constants"));
			context.setAttribute("JSONUtil", staticModels
					.get("com.googlecode.jsonplugin.JSONUtil"));
			context.setAttribute("JptLocalizedTextUtil", staticModels
					.get("${package}.util.JptLocalizedTextUtil"));

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

}
