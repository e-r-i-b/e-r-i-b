package com.rssl.phizic.web.certification;

import com.rssl.phizic.business.BusinessException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.servlet.ServletContext;

/**
 * @author Omeliyanchuk
 * @ created 22.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class TemporyFileHelper
{
	/**
	 * выдает путь к временной директории
	 * @param servletContext
	 * @return
	 */
	public static String getTemporaryDirPath(ServletContext servletContext)
	{
	    File tempDir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		return tempDir.getAbsolutePath();
	}

	/**
	 * Открывает файл из временной директории как поток
	 * @param servletContext
	 * @param fileName имя файла
	 * @return
	 * @throws BusinessException файл не найден
	 */
	public static InputStream getTempFileStream(ServletContext servletContext, String fileName) throws BusinessException
	{
		try
		{
			String filePath = getTemporaryDirPath(servletContext) + fileName;
			File file = new File(filePath);
			InputStream inStream = new FileInputStream(file);
			return inStream;
		}
		catch(FileNotFoundException ex)
		{
			throw new BusinessException("Файл не найден", ex);
		}
	}

	public static void deleteTempFile(ServletContext servletContext, String fileName) throws BusinessException
	{
		String filePath = getTemporaryDirPath(servletContext) + fileName;
		File file = new File(filePath);
		if(!file.delete() )
			throw new BusinessException("Невозможно удалить временный файл:"+filePath);
	}
}
