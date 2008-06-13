package ${package}.util;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import ${package}.Constants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

public class JptLocalizedTextUtil {

	private static final Log logger = LogFactory
			.getLog(JptLocalizedTextUtil.class);

	private static final String DATE_FORMAT_KEY = "date.format";

	private static final String LONG_DATE_FORMAT_KEY = "date.format.long";

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	private static final String DEFAULT_LONG_DATE_FORMAT = "yyyy'年'MM'月'dd'日'";

	private static JptLocalizedTextUtil defaultInst = new JptLocalizedTextUtil(
			ResourceBundle.getBundle(Constants.BUNDLE_KEY, LocaleContextHolder
					.getLocale()));

	private ResourceBundle bundle;

	private JptLocalizedTextUtil(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public static JptLocalizedTextUtil getInstance() {
		return defaultInst;
	}

	public static JptLocalizedTextUtil getInstance(ResourceBundle bundle) {
		return new JptLocalizedTextUtil(bundle);
	}

	public static String getDefaultDateFormat(Locale locale) {
		String defaultFormat = null;
		try {
			defaultFormat = ((SimpleDateFormat) DateFormat.getDateInstance(
					DateFormat.MEDIUM, locale)).toPattern();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		if (defaultFormat == null || "".equals(defaultFormat)) {
			defaultFormat = DEFAULT_DATE_FORMAT;
		}
		return defaultFormat;
	}

	public static String getDefaultLongDateFormat(Locale locale) {
		String defaultFormat = null;
		try {
			defaultFormat = ((SimpleDateFormat) DateFormat.getDateInstance(
					DateFormat.LONG, locale)).toPattern();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		if (defaultFormat == null || "".equals(defaultFormat)) {
			defaultFormat = DEFAULT_LONG_DATE_FORMAT;
		}
		return defaultFormat;
	}

	public static String getDayOfWeek(Locale locale) {
		return new SimpleDateFormat("EEEE", locale).format(new Date());
	}

	public static String getAMPM(Locale locale) {
		return new SimpleDateFormat("a", locale).format(new Date());
	}

	public static List getCountries(Locale locale) {

		List<LabelValue> countries = new ArrayList<LabelValue>();

		for (Locale loc : Locale.getAvailableLocales()) {
			String value = loc.getCountry();
			String label = loc.getDisplayCountry(locale);
			if (!"".equals(value) && !"".equals(label)) {
				LabelValue country = new LabelValue(label, value);
				if (!countries.contains(country)) {
					countries.add(new LabelValue(label, value));
				}
			}
		}

		return countries;

	}

	public String getText(String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		return key;
	}

	public String getText(String key, Object args) {
		try {
			if (args == null) {
				return bundle.getString(key);
			}
			Object[] argsArray = null;
			if (args instanceof Object[]) {
				argsArray = (Object[]) args;
			} else if (args instanceof Collection) {
				argsArray = ((Collection) args).toArray();
			}
			return new MessageFormat(bundle.getString(key), bundle.getLocale())
					.format(argsArray);
		} catch (MissingResourceException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		return key;
	}

	public String getDateFormat() {
		String dateFormat = null;
		try {
			dateFormat = bundle.getString(DATE_FORMAT_KEY);
		} catch (MissingResourceException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		if (dateFormat == null || "".equals(dateFormat)) {
			dateFormat = getDefaultDateFormat(bundle.getLocale());
		}
		return dateFormat;
	}

	public String getLongDateFormat() {
		String dateFormat = null;
		try {
			dateFormat = bundle.getString(LONG_DATE_FORMAT_KEY);
		} catch (MissingResourceException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		if (dateFormat == null || "".equals(dateFormat)) {
			dateFormat = getDefaultLongDateFormat(bundle.getLocale());
		}
		return dateFormat;
	}

	public String getDate() {
		return new SimpleDateFormat(getDateFormat(), bundle.getLocale())
				.format(new Date());
	}

	public String getLongDate() {
		return new SimpleDateFormat(getLongDateFormat(), bundle.getLocale())
				.format(new Date());
	}

}