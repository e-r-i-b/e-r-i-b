package com.rssl.phizic.web.security.captcha;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.rssl.phizic.auth.modes.CaptchaServiceSingleton;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Krenev
 * @ created 27.08.2008
 * @ $Author$
 * @ $Revision$
 */

public class CaptchaServlet extends HttpServlet
{
	public void init(ServletConfig servletConfig) throws ServletException
	{
		super.init(servletConfig);
	}

	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
	{

		// the output stream to render the captcha image as jpeg into
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try
		{
			// get the session id that will identify the generated captcha.
			String captchaId = getCaptchaId(httpServletRequest);
			// call the ImageCaptchaService getChallenge method
				BufferedImage challenge =
			        CaptchaServiceSingleton.getInstance().getImageChallengeForID(captchaId,
			                httpServletRequest.getLocale());


			// a jpeg encoder
			JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
			jpegEncoder.encode(challenge);
		}
		catch (IllegalArgumentException e)
		{
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		catch (CaptchaServiceException e)
		{
			httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

		// flush it in the response
		httpServletResponse.setHeader("Cache-Control", "no-store");
		httpServletResponse.setHeader("Pragma", "no-cache");
		httpServletResponse.setDateHeader("Expires", 0);
		httpServletResponse.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}

	private String getCaptchaId(HttpServletRequest request)
	{
		String path = request.getServletPath();
		return path.substring(1,path.indexOf("-captcha"));
	}
}