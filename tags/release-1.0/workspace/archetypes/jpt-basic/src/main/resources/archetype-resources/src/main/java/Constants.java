package ${package};

import ${package}.commons.Config;
import ${package}.commons.ReloadableConfig;

public class Constants {

	public static final Config CONFIG = new ReloadableConfig(
			"classpath:jpt.properties");

	public static final String BUNDLE_KEY = "ApplicationResources";

	public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";

	public static final String LOGIN_USER = "loginUser";

	//
	// HTTP响应头部
	//

	public static final String HEADER_ACCEPT_ENCODING = "accept-encoding";

	public static final String HEADER_CACHE_CONTROL = "Cache-Control";

	public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";

	public static final String HEADER_PRAGMA = "Pragma";

	public static final String HEADER_USER_AGENT = "user-agent";

	public static final String HEADER_DATE_EXPIRES = "Expires";

	//
	// REQUEST参数
	//

	public static final String REQUEST_PARAM_TARGET_URL = "targetUrl";

	public static final String REQUEST_PARAM_EXTRA_PARAMS = "extraParams";

	//
	// 可配置的常量
	//
	
	/**
	 * @return 重新授权后是否需要重新登录
	 */
	public static boolean isRelogin() {
		return CONFIG.getBoolean("security.authority.relogin");
	}

	/**
	 * @return 分页时的页面大小
	 */
	public static int getPagerLimit() {
		return CONFIG.getInteger("pager.limit", 10);
	}

}
