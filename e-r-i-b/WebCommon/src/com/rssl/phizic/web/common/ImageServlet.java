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
	// ��������� ���������
	private static final String CONTENT_TYPE = "application/octet-stream";

	private static final ImageService imageService = new ImageService();

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		// �������������� MIME ��������
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//��� ATM ������ ����������� �������� ����������� ��� �����������
		if (!SecurityUtil.isAuthenticationComplete() && !ApplicationUtil.isATMApi())
		{
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "�� �� ������������ � �������.");
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
				// ����������� MIME ���� �����������
				MimeType mimeTypes = MimeUtil.getMostSpecificMimeType(MimeUtil.getMimeTypes(data));
				response.setContentType(mimeTypes.toString());
				response.addHeader("Content-Disposition", "attachment; filename=img."+mimeTypes.getSubType());
			}
			catch (Exception e)
			{
				response.setContentType(CONTENT_TYPE);
				// �� ���� �� ��������� ������ ���������
				response.addHeader("Content-Disposition", "attachment; filename=img.jpg");
			}

			if (image != null)
				response.addHeader("Last-Modified", image.getUpdateTime().toString()); // GMT ����� ��������� ����������� �����������
			
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
	
   // �������� ����� post ��� iPhone
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
		doGet(request, response);
		}
		catch (Exception e)
		{
			// �������� ������
			ExceptionLogHelper.writeLogMessage(e);
			// ���� ��������� ������ �������� ������������ ������� ���������� �������� ������ 404
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
