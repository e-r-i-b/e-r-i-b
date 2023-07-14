package com.rssl.phizic.web.common;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.web.util.ExceptionLogHelper;
import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class ImageServlet extends HttpServlet
{
	// дефолтный заголовок
	private static final String CONTENT_TYPE = "application/octet-stream";

	private static final ImageService imageService = new ImageService();

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		// инициализируем MIME детектор
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//Для ATM делаем возможность получить изображения без авторизации
		if (!SecurityUtil.isAuthenticationComplete() && !ApplicationUtil.isATMApi())
		{
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Вы не авторизованы в системе.");
			return;
		}
		Long id = Long.valueOf(request.getParameter("id"));
		ByteArrayOutputStream byteArrayOutputStream = null;
		OutputStream out = null;
		try
		{
			Image image = imageService.findById(id, null);
			byte[] data = image != null ? imageService.loadImageData(image, null) : new byte[0];
			byteArrayOutputStream = new ByteArrayOutputStream();
			byteArrayOutputStream.write(data);

			try
			{
				// определение MIME типа изображения
				MimeType mimeTypes = MimeUtil.getMostSpecificMimeType(MimeUtil.getMimeTypes(data));
				response.setContentType(mimeTypes.toString());
				response.addHeader("Content-Disposition", "attachment; filename=img."+mimeTypes.getSubType());
			}
			catch (Exception e)
			{
				response.setContentType(CONTENT_TYPE);
				// на надо ли дефолтные кривые заголовки
				response.addHeader("Content-Disposition", "attachment; filename=img.jpg");
			}

			if (image != null)
				response.addHeader("Last-Modified", image.getUpdateTime().toString()); // GMT время последней модификации изображения
			
			out = response.getOutputStream();
			byteArrayOutputStream.writeTo(out);
		}
		catch (BusinessException e)
		{
			throw new ServletException(e);
		}
		finally
		{
			if (byteArrayOutputStream != null)
			{
				byteArrayOutputStream.close();
			}
			if (out != null)
			{
				out.flush();
				out.close();
			}
		}
	}
	
   // добавлен метод post для iPhone
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
		doGet(request, response);
		}
		catch (Exception e)
		{
			// логируем ошибку
			ExceptionLogHelper.writeLogMessage(e);
			// если произошла ошибка согласно спецификации клиенту необходимо передать статус 404
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
