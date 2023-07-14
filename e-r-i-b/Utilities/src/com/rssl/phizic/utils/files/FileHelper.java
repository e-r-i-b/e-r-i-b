package com.rssl.phizic.utils.files;

import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Properties;

/**
 * @author Roshka
 * @ created 22.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class FileHelper
{
	private static final Log log = LogFactory.getLog(FileHelper.class);

	private static String getTempDirectoryPath()
	{
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * ������� ���� �� ������
	 * @param inputStream �����
	 * @param fullPath ������� (������ ����)
	 * @return ���� � ������� ��������
	 * @throws IOException
	 */
	public static File write(InputStream inputStream, String fullPath) throws IOException
	{
		File file = new File(fullPath);
		FileOutputStream fileOutputStream = null;
		try
		{
			fileOutputStream = new FileOutputStream(file);
			IOUtils.copy(inputStream, fileOutputStream);
			fileOutputStream.flush();
		}
		finally
		{
			if( fileOutputStream != null )
			    fileOutputStream.close();
		}
		return file;
	}

	/**
	 * ������� ��������� ���� �� ������
	 * @param inputStream �����
	 * @return ���� � ������� ��������
	 * @throws IOException
	 */
	public static File writeTmp(InputStream inputStream) throws IOException
	{
		String fileName = new RandomGUID().getStringValue();
		return writeToTempDirectory(inputStream, fileName);
	}

	/**
	 * ������� ��������� ���� �� ������
	 * @param inputStream �����
	 * @param fileName ��� �����
	 * @return ���� � ������� ��������
	 * @throws IOException
	 */
	public static File writeToTempDirectory(InputStream inputStream, String fileName) throws IOException
	{
		return write(inputStream, getTempDirectoryPath() + fileName);
	}

	/**
	 * ������� ��������� ���� �� ������� ����
	 * @param data ������ ����
	 * @param fileName ��� �����
	 * @return ���� � ������� ��������
	 * @throws IOException
	 */
	public static File writeToTempDirectory(byte[] data, String fileName) throws IOException
	{
		return write(new ByteArrayInputStream(data), getTempDirectoryPath() + fileName);
	}

	/**
	 * ��������� ��������� ���� � ������ ����
	 * @param fileName ��� �����
	 * @return ������ �� �����
	 * @throws IOException
	 */
	public static byte[] readTempDirectoryFile(String fileName) throws IOException
	{
		String tmpPath = getTempDirectoryPath();
		File file = new File(tmpPath.concat(fileName));
		if (!file.exists())
			return new byte[0];

		FileInputStream fileInputStream = null;
		try
		{
			fileInputStream = new FileInputStream(file);
			return readFile(fileInputStream);
		}
		finally
		{
			if (fileInputStream != null)
				fileInputStream.close();
		}
	}

	/**
	 * ������� ��������� ����
	 * @param fileName ��� �����
	 * @return ������� ��� ���
	 * @throws IOException
	 */
	public static boolean deleteTempDirectoryFile(String fileName) throws IOException
	{
		return new File(getTempDirectoryPath().concat(fileName)).delete();
	}

	/**
	 * ������� ������� (���� ���� ����� ������ �� ������)
	 * @param pathName - ����
	 * @return ��������� ����������
	 */
	public static File createDirectory(String pathName)
	{
		File file = new File(pathName);
		if ( !file.exists() )
		{
			if (!file.mkdirs())
				throw new RuntimeException("������ ��� �������� �������� " + pathName);
		}
		return file;
	}

	public static byte[] readFile(InputStream inStream) throws IOException
	{
		int len = -1, curPos = 0;
		byte[] fullBuffer = null;

		while( (len = inStream.available()) > 0 )
		{
			int newLen = len + curPos;
			byte[] newBuffer = new byte[newLen];
			if(fullBuffer != null)
				System.arraycopy(fullBuffer,0,newBuffer,0, curPos);
			fullBuffer = newBuffer;

			if(inStream.read(fullBuffer, curPos, len)!=len)
				throw new RuntimeException("������ ������ �� �����");
			curPos += len;
		}
		return fullBuffer;
	}

	/**
	 * ������� �����
	 * @param path - ����
	 * @throws IOException
	 */
	public static void deleteDirectory(String path) throws IOException
	{
		deleteDirectory(path, true);
	}

	/**
	 * ������� �����
	 * @param path ����
	 * @param exceptionInError ����������� ���������� ��� ������ ��������.
	 * @throws IOException
	 */
	public static void deleteDirectory(String path, boolean exceptionInError) throws IOException
	{
		File file = new File(path);

		if ( !file.exists() && exceptionInError )
			throw new IOException("���� " + path + " �� ����������");

		if (file.isDirectory())
		{
			File[] files = file.listFiles();
			for (int i=0; i<files.length; i++)
				deleteDirectory(files[i].getAbsolutePath(), exceptionInError);
		}

		if (!file.delete() && exceptionInError)
			throw new RuntimeException("������ ��� �������� ����� " + path);
	}

	/**
	 * ������� ����, ���� �� ����
	 * @param file - ����
	 * @throws IOException ������� �� �������
	 */
	public static void deleteFileIfExists(File file) throws IOException
	{
		if (!file.exists())
			return;

		if (!file.delete())
			throw new IOException("�� ������� ������� ���� " + file.getAbsolutePath());
	}

	/**
	 * ����������� ���� � ���������� ��� ������� ��
	 * @param path - ����
	 * @return ���������� ����
	 */
	public static String normalizePath(String path)
	{
		if (path == null)
			return path;

		return path.replace("\\", File.separator);
	}

	/**
	 * ����������� ���� ��, � ���� ��� ��������
	 * @param path
	 * @return
	 */
	public static String denormalizePath(String path)
	{
		if (path == null)
			return path;

		return path.replace(File.separator,"\\");
	}

	/**
	 * ���������� ���������� �����
	 * @param file - ����
	 * @return ���������� ��� null, ���� � ����� ��� ����������
	 */
	public static String getFileExtension(File file)
	{
		return getFileExtension(file.getName());
	}

	/**
	 * ���������� ���������� �����
	 * @param filename - ��� �����
	 * @return ���������� ��� null, ���� � ����� ��� ����������
	 */
	public static String getFileExtension(String filename)
	{
		if (StringHelper.isEmpty(filename))
			throw new IllegalArgumentException("�������� 'filename' �� ����� ���� ������");

		int dot = filename.lastIndexOf('.');
		return (dot!=-1 && dot!=filename.length()-1) ? filename.substring(dot+1) : null;
	}

	/**
	 * ���������� ���� � ���� �����������.
	 * @param path0 ����
	 * @param fileName ��� �����
	 * @return ���� � ���� �����������
	 */
	public static String getCurrentFilePath(String path0, String fileName) throws IOException
	{
		String path = new File(path0).getCanonicalPath();
		//���������� ������ ����� ������ root � �������� �����,
		//����� �� ��������� ������ ���������.
		String[] pathArr = path.split(":");
		if (!(StringHelper.equals(pathArr[pathArr.length - 1], File.separator)))
			return new File(path).getCanonicalPath() + File.separator + fileName;
		return path + fileName;
	}

	/**
	 * ��������� ���� �� ����������� ������
	 * @param file
	 * @return
	 */
	public static boolean canWrite(File file)
	{
		if (file != null)
		if (file.canWrite())
			return true;
		log.error("��� ���� �� ������ � ���� "+file.getName());
		return false;
	}

	/**
	 *
	 * �������� ������ ������� (���� ����-��������) � ������ Properties  
	 *
	 * @param file ���� �������� ����������� ��������
	 * @param properties
	 * @throws IOException
	 */
	public static void loadProperties(File file, Properties properties) throws IOException
	{
		InputStream iStream = null;
		try
		{
			iStream = new FileInputStream(file);
			properties.load(iStream);
		}
		finally
		{
			if (iStream != null)
			{
				iStream.close();
			}
		}
	}

	/**
	 *
	 * ��������� ����� ������� (���� ����-��������) � ����.
	 * ���������� ������ � ������������ ����! 
	 *
	 * @param file ���� ��� ������
	 * @param properties
	 * @throws IOException
	 */
	public static void storeProperties(File file, Properties properties) throws IOException
	{
		OutputStream oStream = null;
		try
		{
			oStream = new FileOutputStream(file);
			properties.store(oStream, null);
		}
		finally
		{
			if (oStream != null)
			{
				oStream.close();
			}
		}
	}
}