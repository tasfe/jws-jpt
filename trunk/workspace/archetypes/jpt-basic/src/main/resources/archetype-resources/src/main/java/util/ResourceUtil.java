package ${package}.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

public class ResourceUtil {

	public static final String RESOURCE_CHARSET = "UTF-8";

	private static final Log logger = LogFactory.getLog(ResourceUtil.class);

	private static final String COMMENT_MARK = "#";

	public static void getResourceAsSet(Set<String> set, String name) {
		BufferedReader in = null;
		String line = null;
		try {
			in = new BufferedReader(getResourceAsReader(name));
			while ((line = in.readLine()) != null) {
				if (line.indexOf(COMMENT_MARK) == -1 && line.length() > 0) {
					set.add(line);
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e);
				}
			}
		}

	}

	public static Reader getResourceAsReader(Resource resource)
			throws UnsupportedEncodingException, IOException {
		return new InputStreamReader(resource.getInputStream(),
				RESOURCE_CHARSET);
	}

	public static Reader getResourceAsReader(String name) throws Exception {
		return new InputStreamReader(new FileInputStream(ResourceUtils
				.getFile(name)), RESOURCE_CHARSET);
	}

}
