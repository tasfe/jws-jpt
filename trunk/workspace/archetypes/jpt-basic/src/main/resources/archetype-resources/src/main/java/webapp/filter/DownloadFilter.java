package ${package}.webapp.filter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ${package}.service.FileManager;
import ${package}.util.FileUtil;

public class DownloadFilter implements Filter {

	private static final String URL_ENC = "UTF-8";

	private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

	private static Map<String, String> contentTypes = new HashMap<String, String>();

	private FilterConfig filterConfig = null;

	private FileManager manager = null;

	static {
		contentTypes.put("htm", "text/html");
		contentTypes.put("html", "text/html");
		contentTypes.put("txt", "text/html");
		contentTypes.put("gif", "image/gif");
		contentTypes.put("bmp", "image/bmp");
		contentTypes.put("jpeg", "image/jpeg");
		contentTypes.put("jpg", "image/jpeg");
		contentTypes.put("png", "image/png");
		contentTypes.put("doc", "application/msword");
		contentTypes.put("dot", "application/msword");
		contentTypes.put("xls", "application/vnd.ms-excel");
		contentTypes.put("xlt", "application/vnd.ms-excel");
		contentTypes.put("pdf", "application/pdf");
		contentTypes.put("rtf", "application/rtf");
		contentTypes.put("zip", "application/zip");
		contentTypes.put("asf", "video/x-ms-asf");
		contentTypes.put("avi", "video/avi");
		contentTypes.put("mpg", "video/mpeg");
		contentTypes.put("mpeg", "video/mpeg");
		contentTypes.put("wav", "audio/wav");
		contentTypes.put("mp3", "audio/mpeg3");
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		String beanName = filterConfig.getInitParameter("manager");
		if (beanName == null) {
			beanName = "fileManager";
		}
		manager = (FileManager) getContext(filterConfig).getBean(beanName);
	}

	public void destroy() {
		filterConfig = null;
		manager = null;
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String realPath = filterConfig.getServletContext().getRealPath(
				java.net.URLDecoder.decode(request.getRequestURI().substring(
						request.getContextPath().length()), URL_ENC));
		File f = manager.getFile(realPath, request.getParameterMap());
		if (f.exists()) {
			String filename = manager.getFilename(realPath, request
					.getParameterMap());
			setContentType(response, filename);
			response.setContentLength((int) f.length());
			if (filename != null) {
				response
						.addHeader("Content-Disposition",
								"attachment;filename="
										+ java.net.URLEncoder.encode(filename,
												URL_ENC));
			}
			FileUtil.copy(new BufferedInputStream(new FileInputStream(f)),
					response.getOutputStream());
		}
		return;
	}

	protected ApplicationContext getContext(FilterConfig filterConfig) {
		return WebApplicationContextUtils
				.getRequiredWebApplicationContext(filterConfig
						.getServletContext());
	}

	protected void setContentType(HttpServletResponse response, String filename) {
		String ext = "";
		int dotidx = filename.lastIndexOf(".");
		if (dotidx != -1) {
			ext = filename.substring(dotidx + 1);
		}
		String contentType = contentTypes.get(ext);
		if (contentType == null) {
			contentType = DEFAULT_CONTENT_TYPE;
		}
		response.setContentType(contentType);
	}

}
