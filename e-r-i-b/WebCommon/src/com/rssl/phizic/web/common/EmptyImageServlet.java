package com.rssl.phizic.web.common;

import com.rssl.phizic.security.SecurityUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Возвращает прозрачный пиксель
 * @author Pankin
 * @ created 29.07.14
 * @ $Author$
 * @ $Revision$
 */
public class EmptyImageServlet extends HttpServlet
{
	private BufferedImage pixel;

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		pixel = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		pixel.setRGB(0, 0, 0xFF);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if (!SecurityUtil.isAuthenticationComplete())
		{
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Вы не авторизованы в системе.");
			return;
		}
		OutputStream out = null;

		try
		{
			resp.setContentType("image/png");
			resp.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			out = resp.getOutputStream();
			ImageIO.write(pixel, "png", out);
		}
		finally
		{
			if (out != null)
			{
				out.flush();
				out.close();
			}
		}
	}
}
