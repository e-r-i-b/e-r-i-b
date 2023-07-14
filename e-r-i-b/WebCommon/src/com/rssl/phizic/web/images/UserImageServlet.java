package com.rssl.phizic.web.images;

import com.rssl.phizic.business.profile.images.ImageService;
import com.rssl.phizic.business.profile.images.UserImageService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.utils.StringHelper;

import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Сервлет, отвечающий за загрузку пользовательских изображений с нашего локального хранилища.
 *
 * @author bogdanov
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UserImageServlet extends HttpServlet
{
	private Log log = PhizICLogFactory.getLog(LogModule.Core);

	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		String tempParam = request.getParameter("temp");
		boolean temp = StringHelper.isNotEmpty(tempParam) && "true".equals(tempParam);
		String fileName = request.getPathInfo();
		if (!fileName.matches("^(/[0-9a-zA-z_]+)?(/[0-9a-fA-F]{2}){4}/[0-9a-fA-F]+_[0-9]+\\.[0-9a-zA-z_]+"))
			return;

		ByteArrayOutputStream image = getImage(fileName, temp, request);
		if (image == null)
			return;

		String ext = ImageService.getFileExtention(fileName);
		response.addHeader("Cache-Control", "must-revalidate,public,max-age=" + (ConfigFactory.getConfig(ProfileConfig.class).getLocalImageTimeMoved()*60));
		response.setContentType("image/" + (ext.equals("jpg") ? "jpeg" : ext));

		OutputStream outputStream = response.getOutputStream();
		image.writeTo(outputStream);
		image.close();
		outputStream.close();
	}

	private ByteArrayOutputStream getImage(String fileName, boolean temp, HttpServletRequest request)
	{
		if (!temp)
		{
			HttpSession session = request.getSession();
			if (session != null)
			{
				if (UserImageService.isMustMovedToStat(fileName))
					log.info("Изображение " + fileName + " загружается не из статических ресурсов.");
			}
		}

		try
		{
			for (File file : UserImageService.get().getImageFiles(fileName, null, temp))
			{
				if (!file.exists())
					continue;

				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				ByteArrayOutputStream baos = new ByteArrayOutputStream((int)file.length());
				BufferedOutputStream bos = new BufferedOutputStream(baos);
				try
				{
					while (true) {
						int b = bis.read();
						if (b == -1)
							break;
						bos.write(b);
					}
				}
				finally
				{
					bis.close();
					bos.flush();
					bos.close();
				}

				return baos;
			}
		}
		catch (Exception e)
		{
			log.error("ошибка получения изображения " + fileName, e);
		}

		log.warn("нет файлов для получения изображения по " + fileName);
		return null;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
