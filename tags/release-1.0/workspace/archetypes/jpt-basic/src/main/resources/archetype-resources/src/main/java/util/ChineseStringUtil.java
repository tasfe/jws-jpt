package ${package}.util;

import java.util.Set;
import java.util.TreeSet;

public class ChineseStringUtil {

	private static final int WORD_MAX_LENGTH = 8;

	private static Set<String> foreign = new TreeSet<String>();

	private static Set<String> numbers = new TreeSet<String>();

	private static Set<String> words = new TreeSet<String>();

	private static Set<String> replaced = new TreeSet<String>();

	static {
		ResourceUtil.getResourceAsSet(numbers, "/se/n.dic");
		ResourceUtil.getResourceAsSet(foreign, "/se/f.dic");
		ResourceUtil.getResourceAsSet(words, "/se/w.dic");
		ResourceUtil.getResourceAsSet(replaced, "/se/r.dic");
	}

	public static Set<String> split(String text) {
		Set<String> result = new TreeSet<String>();
		int[] limits = buildLimits(text);
		for (int i = 0; i < limits.length; i++) {
			int next = i + limits[i];
			if (limits[i] > 0 && next <= limits.length) {
				String word = text.substring(i, next).trim();
				if (word.length() > 1) {
					result.add(word);
				}
			}
		}
		return result;
	}

	public static void removeReplacedWords(Set<String> set) {
		set.removeAll(replaced);
	}

	public static String replace(String text, String replacement) {
		if (text == null || "".equals(text)) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		int len = text.length();
		for (int i = 0; i < len;) {
			int limit = match(text, i, WORD_MAX_LENGTH, replaced);
			if (limit == 1) {
				result.append(text.charAt(i));
			} else {
				result.append(replacement);
			}
			i += limit;
		}

		return result.toString();
	}

	public static int[] buildLimits(String text) {
		int len = text.length();
		int[] limits = new int[len];

		for (int i = 0; i < len;) {
			if (Character.UnicodeBlock.of(text.charAt(i)) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
				limits[i] = match(text, i, WORD_MAX_LENGTH, words);
				i += limits[i];

			} else if (Character.isWhitespace(text.charAt(i))) {
				int limit = 1;
				while (i + limit < len
						&& Character.isWhitespace(text.charAt(i + limit))) {
					limit++;
				}
				limits[i] = limit;
				i += limit;
			} else if (StringUtil.isASCIIAlpha(text.charAt(i))) {
				int limit = 1;
				while (i + limit < len
						&& StringUtil.isASCIIAlpha(text.charAt(i + limit))) {
					limit++;
				}
				limits[i] = limit;
				i += limit;
			} else if (Character.isDigit(text.charAt(i))) {
				int limit = 1;
				while (i + limit < len
						&& Character.isDigit(text.charAt(i + limit))) {
					limit++;
				}
				limits[i] = limit;
				i += limit;

			} else {
				limits[i] = 1;
				i++;
			}
		}

		for (int i = 0; i < len; i++) {
			if (limits[i] > 0) {
				int next = i + limits[i];
				while (next < len && next + limits[next] < len
						&& isAllForeign(text.substring(i, next + limits[next]))) {
					int t = limits[next];
					limits[next] = 0;
					limits[i] = limits[i] + t;
					next = i + limits[i];
				}
			}
		}

		for (int i = 0; i < len; i++) {
			if (limits[i] > 0) {
				int next = i + limits[i];
				while (next < len && next + limits[next] < len
						&& isAllNumber(text.substring(i, next + limits[next]))) {
					int t = limits[next];
					limits[next] = 0;
					limits[i] = limits[i] + t;
					next = i + limits[i];
				}
			}
			i++;
		}

		return limits;
	}

	public static int match(String text, int start, int max, Set dic) {
		int limit = max;
		int len = text.length();
		if (start + limit > len) {
			limit = len - start;
		}
		while (start + limit <= len && limit > 1) {
			if (dic.contains(text.substring(start, start + limit))) {
				break;
			}
			limit--;
		}
		return limit;
	}

	public static boolean isAllForeign(String word) {
		boolean result = true;
		for (int i = 0; i < word.length(); i++) {
			if (!foreign.contains(word.substring(i, i + 1))) {
				result = false;
				break;
			}
		}

		return result;
	}

	public static boolean isAllNumber(String word) {
		boolean result = true;
		for (int i = 0; i < word.length(); i++) {
			if (!numbers.contains(word.substring(i, i + 1))) {
				result = false;
				break;
			}
		}

		return result;
	}

}
