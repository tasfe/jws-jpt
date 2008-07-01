package ${package}.security.jcaptcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import ${package}.Constants;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageCaptchaServlet extends HttpServlet {

	private static final long serialVersionUID = 3258417209566116145L;

	private static final String CAPTCHA_SERVICE_NAME = "captchaServiceName";

	private String captchaServiceName = "imageCaptchaService";

	public void init(ServletConfig servletConfig) throws ServletException {
		if (StringUtils.isNotBlank(servletConfig
				.getInitParameter(CAPTCHA_SERVICE_NAME))) {
			captchaServiceName = servletConfig
					.getInitParameter(CAPTCHA_SERVICE_NAME);
		}
		super.init(servletConfig);
	}

	@Override
	protected void doGet(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			ApplicationContext ctx = WebApplicationContextUtils
					.getRequiredWebApplicationContext(getServletContext());
			ImageCaptchaService imageCaptchaService = (ImageCaptchaService) ctx
					.getBean(captchaServiceName);

			String captchaId = httpServletRequest.getSession().getId();
			BufferedImage challenge = imageCaptchaService
					.getImageChallengeForID(captchaId, httpServletRequest
							.getLocale());

			JPEGImageEncoder jpegEncoder = JPEGCodec
					.createJPEGEncoder(jpegOutputStream);
			jpegEncoder.encode(challenge);
		} catch (IllegalArgumentException e) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} catch (CaptchaServiceException e) {
			httpServletResponse
					.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

		httpServletResponse.setHeader(Constants.HEADER_CACHE_CONTROL,
				"no-store");
		httpServletResponse.setHeader(Constants.HEADER_PRAGMA, "no-cache");
		httpServletResponse.setDateHeader(Constants.HEADER_DATE_EXPIRES, 0);
		httpServletResponse.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = httpServletResponse
				.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}
}
