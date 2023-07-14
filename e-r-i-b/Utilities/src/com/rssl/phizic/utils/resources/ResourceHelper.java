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
	 * ��������� ������ � �����
	 * @param resourceName - ���
	 * @return ����� � ������� �������
	 */
	public static InputStream loadResourceAsStream(String resourceName)
	{
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
		if (inputStream == null)
			throw new RuntimeException("�� ������ ������ - " + resourceName);

		return inputStream;
	}

	/**
	 * ��������� ������ � �����
	 * @param resourceName - ���
	 * @return ����� � ������� �������
	 */
	public static Reader loadResourceAsReader(String resourceName)
	{
		InputStream inputStream = loadResourceAsStream(resourceName);
		return new InputStreamReader(inputStream);
	}
	/**
	 * ��������� ������ � ������
	 * @param resourceName - ��� �������

	 * @return ������ � ������� �������
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
	 * ��������� ������ � ������
	 * @param resourceName - ��� �������
	 * @param charsetName - �������������� ��������� (never null)
	 * @return ������ � ������� �������
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
	 * ��������� ������ � ����.
	 * @param resourceName - ��� �������
	 * @param targetDirectory - �������
	 * @return ��������� ����
	 */
	public static File saveFileFromResource(String resourceName, String targetDirectory)
	{
		return saveFileFromResource(resourceName, targetDirectory, false);
	}

	/**
	 * ��������� ������ � ����.
	 * @param resourceName - ��� �������
	 * @param targetDirectory - �������
	 * @param ignoreInUse ������������ ���� ���� ����� ������ ���������
	 * @return ��������� ����
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
				log.warn("������ ���������� ����: " + file + " ������ ���������������");
			}
			else
				throw new RuntimeException("���� ����� ������ ���������" + file, e);
			
		}
		catch (IOException e)
		{
			throw new RuntimeException("������ ��� ���������� ������� " + resourceName, e);
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