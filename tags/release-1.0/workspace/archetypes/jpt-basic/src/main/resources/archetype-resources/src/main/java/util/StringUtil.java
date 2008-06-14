package ${package}.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static final String STRING_SEPARATOR = ",";

	public interface ReplaceInterceptor {

		public boolean preReplace(Matcher matcher);

		public void postReplace(Matcher matcher);

	}

	public static String getString(Map parameters, String name) {
		Object value = parameters.get(name);
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			return (String) value;
		}
		if (value instanceof String[]) {
			return ((String[]) value)[0];
		}
		return value.toString();
	}

	public static boolean isASCIIAlpha(char ch) {
		return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
	}

	public static boolean isIdentifier(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		if (sz > 0 && Character.isDigit(str.charAt(0))) {
			return false;
		}
		for (int i = 0; i < sz; i++) {
			char ch = str.charAt(i);
			if (!isASCIIAlpha(ch) && !Character.isDigit(ch) && ch != '_') {
				return false;
			}
		}
		return true;
	}

	public static int indexOfWord(String str, String word) {
		int len = word.length();
		int idx = -len;
		do {
			idx = str.indexOf(word, idx + len);
		} while (idx > 0
				&& (!Character.isWhitespace(str.charAt(idx - 1)) || !Character
						.isWhitespace(str.charAt(idx + len))));
		return idx;
	}

	public static String format(String html) {
		html = stripComment(html);
		html = stripScript(html);
		return html;
	}

	public static String html2text(String html) {
		html = stripComment(html);
		html = stripScript(html);
		html = stripStyle(html);
		html = stripTags(html, null);
		html = stripWS(html);
		html = unescapeHtml(html);
		return html;
	}

	public static String stripWS(String html) {
		final String regex1 = "(?i)　+";
		final String regex2 = "(?i)(&nbsp;)+";
		final String regex3 = "(?i)\\s+";

		html = html.replaceAll(regex1, " ");
		html = html.replaceAll(regex2, " ");
		html = html.replaceAll(regex3, " ");

		return html;
	}

	public static String stripComment(String html) {
		final String regex1 = "(?i)\\s*<\\!DOCTYPE\\s+[^>]*>\\s*";
		final String regex2 = "(?s)\\s*<\\!--.*?-->\\s*";

		html = html.replaceAll(regex1, "");
		html = html.replaceAll(regex2, "");

		return html;
	}

	public static String stripScript(String html) {
		final String regex1 = "(?is)\\s*<([^>]*)>.*?<\\/([^>]*)>\\s*";
		final String regex2 = "(?i)\\s*<([^>]*)>\\s*";
		final String[] scriptTags = { "SCRIPT", "OBJECT", "EMBED", "APPLET" };

		ReplaceInterceptor interceptor = new ReplaceInterceptor() {
			public boolean preReplace(Matcher matcher) {
				for (String tag : scriptTags) {
					String group = matcher.group(1).toLowerCase();
					tag = tag.toLowerCase();
					if (group.equals(tag)
							|| group.equals("/" + tag)
							|| (group.startsWith(tag) && !isASCIIAlpha(group
									.charAt(tag.length())))) {
						if (matcher.groupCount() == 2) {
							return matcher.group(2).equalsIgnoreCase(tag);
						}
						return true;
					}
				}
				return false;
			}

			public void postReplace(Matcher matcher) {
			}
		};

		html = replace(html, regex1, "", interceptor);
		html = replace(html, regex2, "", interceptor);

		return html;
	}

	public static String stripStyle(String html) {
		final String regex1 = "(?is)<STYLE\\s*[^>]*>.*?<\\/STYLE>";
		final String regex2 = "(?i)<(\\w[^>]*)\\sclass\\s*=\\s*((\"([^\"]*)\")|([^\\s>]*))([^>]*)>";

		html = html.replaceAll(regex1, "");
		html = html.replaceAll(regex2, "<$1$6>");

		return html;
	}

	public static String stripTags(String html, final String[] ignoredTags) {
		final String regex = "(?i)\\s*<([^>]*)>\\s*";

		ReplaceInterceptor interceptor = new ReplaceInterceptor() {
			public boolean preReplace(Matcher matcher) {
				if (ignoredTags != null) {
					for (String tag : ignoredTags) {
						String group = matcher.group(1).toLowerCase();
						tag = tag.toLowerCase();
						if (group.equals(tag)
								|| group.equals("/" + tag)
								|| (group.startsWith(tag) && !isASCIIAlpha(group
										.charAt(tag.length())))) {
							return false;
						}
					}
				}
				return true;
			}

			public void postReplace(Matcher matcher) {
			}
		};

		html = stripMSWordTags(html);
		html = replace(html, regex, "", interceptor);

		return html;
	}

	public static String stripMSWordTags(String html) {
		final String regex1 = "(?i)<o:p>\\s*<\\/o:p>";
		final String regex2 = "(?is)<o:p>.*?<\\/o:p>";

		html = html.replaceAll(regex1, "");
		html = html.replaceAll(regex2, "&nbsp;");

		return html;
	}

	public static String replace(String str, String regex, String replacement,
			ReplaceInterceptor interceptor) {
		Matcher matcher = Pattern.compile(regex).matcher(str);
		matcher.reset();
		if (matcher.find()) {
			StringBuffer sb = new StringBuffer();
			do {
				if (interceptor.preReplace(matcher)) {
					matcher.appendReplacement(sb, replacement);
				}
				interceptor.postReplace(matcher);
			} while (matcher.find());
			matcher.appendTail(sb);
			return sb.toString();
		}
		return str;
	}

	public static String escapeSqlLikeExpr(String expr) {
		final String regex = "(?s)([%_])";
		return expr.replaceAll(regex, "\\\\$1");
	}

	private static String unescapeHtml(String html) {
		html = html.replaceAll("&amp;", "&");
		html = html.replaceAll("&lt;", "<");
		html = html.replaceAll("&gt;", ">");
		html = html.replaceAll("&quot;", "\"");
		html = html.replaceAll("&#146;", "'");
		return html;
	}

	public static final String SLASH = "/";

}
