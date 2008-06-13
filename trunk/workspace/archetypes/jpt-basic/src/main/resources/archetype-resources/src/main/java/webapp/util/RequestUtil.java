package ${package}.webapp.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestUtil {

	public static void setCookie(HttpServletResponse response, String name,
			String value, String path) {
		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(false);
		cookie.setPath(path);
		cookie.setMaxAge(3600 * 24 * 30);

		response.addCookie(cookie);
	}

	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		if (cookie != null) {
			return cookie.getValue();
		}
		return null;
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					if (!"".equals(cookie.getValue())) {
						return cookie;
					}
				}
			}
		}

		return null;
	}

	public static void deleteCookie(HttpServletResponse response,
			Cookie cookie, String path) {
		if (cookie != null) {
			cookie.setMaxAge(0);
			cookie.setPath(path);
			response.addCookie(cookie);
		}
	}

	public static void mergeQueryParameters(HttpServletRequest request,
			Map<String, Object> parameters) {

		Map<String, Object> queryParameters = new HashMap<String, Object>();

		parseQueryParameters(request, queryParameters);

		mergeParameters(parameters, queryParameters);
	}

	public static void mergeParameters(Map<String, Object> dest,
			Map<String, Object> src) {
		for (Map.Entry<String, Object> entry : src.entrySet()) {
			Object value = dest.get(entry.getKey());
			if (value == null) {
				dest.put(entry.getKey(), entry.getValue());
			} else {
				dest.put(entry.getKey(), mergeValues(value, entry.getValue()));
			}
		}
	}

	public static void parseQueryParameters(HttpServletRequest request,
			Map<String, Object> parameters) {
		String encoding = request.getCharacterEncoding();
		if (encoding == null || "".equals(encoding)) {
			encoding = "UTF-8";
		}
		try {
			parseQueryParameters(parameters, request.getQueryString(), encoding);
		} catch (UnsupportedEncodingException e) {

		}
	}

	public static void parseQueryParameters(Map<String, Object> map,
			String queryString, String encoding)
			throws UnsupportedEncodingException {
		if (queryString != null && queryString.length() > 0) {
			byte[] data = null;
			try {
				if (encoding == null) {
					data = queryString.getBytes();
				} else {
					data = queryString.getBytes(encoding);
				}
			} catch (UnsupportedEncodingException uee) {
			}
			parseParameters(map, data, encoding);
		}
	}

	public static void parseParameters(Map<String, Object> map, byte[] data,
			String encoding) throws UnsupportedEncodingException {
		if (data != null && data.length > 0) {
			int ix = 0;
			int ox = 0;
			String key = null;
			String value = null;
			while (ix < data.length) {
				byte c = data[ix++];
				switch ((char) c) {
				case '&':
					value = new String(data, 0, ox, encoding);
					if (key != null) {
						putMapEntry(map, key, value);
						key = null;
					}
					ox = 0;
					break;
				case '=':
					if (key == null) {
						key = new String(data, 0, ox, encoding);
						ox = 0;
					} else {
						data[ox++] = c;
					}
					break;
				case '+':
					data[ox++] = (byte) ' ';
					break;
				case '%':
					data[ox++] = (byte) ((convertHexDigit(data[ix++]) << 4) + convertHexDigit(data[ix++]));
					break;
				default:
					data[ox++] = c;
				}
			}

			if (key != null) {
				value = new String(data, 0, ox, encoding);
				putMapEntry(map, key, value);
			}
		}
	}

	private static byte convertHexDigit(byte b) {
		if (b >= '0' && b <= '9') {
			return (byte) (b - '0');
		}
		if (b >= 'a' && b <= 'f') {
			return (byte) (b - 'a' + 10);
		}
		if (b >= 'A' && b <= 'F') {
			return (byte) (b - 'A' + 10);
		}
		return 0;
	}

	private static void putMapEntry(Map<String, Object> map, String name,
			String value) {
		String[] newValues = null;
		String[] oldValues = (String[]) map.get(name);
		if (oldValues == null) {
			newValues = new String[1];
			newValues[0] = value;
		} else {
			newValues = new String[oldValues.length + 1];
			System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
			newValues[oldValues.length] = value;
		}
		map.put(name, newValues);
	}

	private static String[] mergeValues(Object values1, Object values2) {
		List<String> list = new ArrayList<String>();
		addStringValues(list, values1);
		addStringValues(list, values2);
		String values[] = new String[list.size()];
		return (String[]) list.toArray(values);
	}

	private static void addStringValues(List<String> list, Object values) {
		if (values != null) {
			if (values instanceof String) {
				list.add((String) values);
			} else if (values instanceof String[]) {
				for (String value : (String[]) values) {
					list.add(value);
				}
			} else {
				list.add(values.toString());
			}
		}
	}

}
