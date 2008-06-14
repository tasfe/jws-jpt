package ${package}.webapp.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import ${package}.Constants;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class LocaleFilter extends OncePerRequestFilter {

	@SuppressWarnings("unchecked")
	public void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String locale = request.getParameter("locale");
		Locale preferredLocale = null;

		if (locale != null) {
			int pos = locale.indexOf('_');
			if (pos != -1) {
				String language = locale.substring(0, pos);
				String country = locale.substring(pos + 1);
				preferredLocale = new Locale(language, country);
			} else {
				preferredLocale = new Locale(locale);
			}
		}

		HttpSession session = request.getSession(false);

		if (session != null) {
			if (preferredLocale == null) {
				preferredLocale = (Locale) session
						.getAttribute(Constants.PREFERRED_LOCALE_KEY);
			} else {
				session.setAttribute(Constants.PREFERRED_LOCALE_KEY,
						preferredLocale);
				Config.set(session, Config.FMT_LOCALE, preferredLocale);
			}

			if (preferredLocale != null
					&& !(request instanceof LocaleRequestWrapper)) {
				request = new LocaleRequestWrapper(request, preferredLocale);
				LocaleContextHolder.setLocale(preferredLocale);
			}
		}

		chain.doFilter(request, response);

		LocaleContextHolder.setLocaleContext(null);
	}
}
