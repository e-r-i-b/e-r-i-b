package com.rssl.phizic.utils.resources;

import com.rssl.phizic.utils.files.FileHelper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

/**
 * @author Roshka
 * @ created 22.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class ResourceHelper
{
	private static final int BUFFER_SIZE = 2048;

	private static final Log log = LogFactory.getLog(ResourceHelper.class);

	/**
	 * Прочитать ресурс в поток
	 * @param resourceName - имя
	 * @return поток с данными ресурса
	 */
	public static InputStream loadResourceAsStream(String resourceName)
	{
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
		if (inputStream == null)
			throw new RuntimeException("Не найден ресурс - " + resourceName);

		return inputStream;
	}

	/**
	 * Прочитать ресурс в ридер
	 * @param resourceName - имя
	 * @return ридер с данными ресурса
	 */
	public static Reader loadResourceAsReader(String resourceName)
	{
		InputStream inputStream = loadResourceAsStream(resourceName);
		return new InputStreamReader(inputStream);
	}
	/**
	 * Прочитать ресурс в строку
	 * @param resourceName - имя ресурса

	 * @return строка с данными ресурса
	 */
	public static String loadResourceAsString(String resourceName) throws IOException
	{
		InputStream stream = loadResourceAsStream(resourceName);
		try
		{
			return IOUtils.toString(stream);
		}
		finally
		{
			try
			{
				stream.close();
			}
			catch (IOException ignore) {}
		}
	}

	/**
	 * Прочитать ресурс в строку
	 * @param resourceName - имя ресурса
	 * @param charsetName - предполагаемая кодировка (never null)
	 * @return строка с данными ресурса
	 */
	public static String loadResourceAsString(String resourceName, String charsetName) throws IOException
	{
		InputStream stream = loadResourceAsStream(resourceName);
		try
		{
			return IOUtils.toString(stream, charsetName);
		}
		finally
		{
			try
			{
				stream.close();
			}
			catch (IOException ignore) {}
		}
	}

	/**
	 * Сохранить ресурс в файл.
	 * @param resourceName - имя ресурса
	 * @param targetDirectory - каталог
	 * @return созданный файл
	 */
	public static File saveFileFromResource(String resourceName, String targetDirectory)
	{
		return saveFileFromResource(resourceName, targetDirectory, false);
	}

	/**
	 * Сохранить ресурс в файл.
	 * @param resourceName - имя ресурса
	 * @param targetDirectory - каталог
	 * @param ignoreInUse игнорировать если файл занят другим процессом
	 * @return созданный файл
	 */
	public static File saveFileFromResource(String resourceName, String targetDirectory, boolean ignoreInUse)
	{
		InputStream inputStream = null;
		String dir = targetDirectory.endsWith(File.separator) ? targetDirectory : targetDirectory + File.separator;
		String fullPath = dir + resourceName;
		File directory = new File(dir);
		if(!directory.exists())
			directory.mkdirs();

		File file = new File(fullPath);

		try
		{
			inputStream = loadResourceAsStream(resourceName);
			file = FileHelper.write(inputStream, fullPath);
		}
		catch (FileNotFoundException e)
		{
			if (file.exists() && ignoreInUse)
			{
				log.warn("Запись невозможна файл: " + file + " ошибка проигнорирована");
			}
			else
				throw new RuntimeException("Файл занят другим процессом" + file, e);
			
		}
		catch (IOException e)
		{
			throw new RuntimeException("Ошибка при сохранении ресурса " + resourceName, e);
		}
		finally
		{
			if (inputStream != null)
			{
				IOUtils.closeQuietly(inputStream);
			}
		}

		return file;
	}
}