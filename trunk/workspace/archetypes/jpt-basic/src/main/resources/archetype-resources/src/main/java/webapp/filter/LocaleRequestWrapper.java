package ${package}.webapp.filter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LocaleRequestWrapper extends HttpServletRequestWrapper {

	private static final Log logger = LogFactory
			.getLog(LocaleRequestWrapper.class);

	private final Locale preferredLocale;

	public LocaleRequestWrapper(HttpServletRequest decorated, Locale userLocale) {
		super(decorated);
		preferredLocale = userLocale;
		if (preferredLocale == null) {
			logger.error("preferred locale = null, it is an unexpected value!");
		}
	}

	@Override
	public Locale getLocale() {
		return preferredLocale == null ? super.getLocale() : preferredLocale;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Enumeration getLocales() {
		if (preferredLocale != null) {
			List<Locale> list = Collections.list(super.getLocales());
			if (list.contains(preferredLocale)) {
				list.remove(preferredLocale);
			}
			list.add(0, preferredLocale);
			return Collections.enumeration(list);
		} else {
			return super.getLocales();
		}
	}

}
