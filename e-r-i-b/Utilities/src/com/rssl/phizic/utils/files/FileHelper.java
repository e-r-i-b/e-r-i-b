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
	 * Создать файл из потока
	 * @param inputStream поток
	 * @param fullPath каталог (должен быть)
	 * @return файл в который записали
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
	 * Создать временный файл из потока
	 * @param inputStream поток
	 * @return файл в который записали
	 * @throws IOException
	 */
	public static File writeTmp(InputStream inputStream) throws IOException
	{
		String fileName = new RandomGUID().getStringValue();
		return writeToTempDirectory(inputStream, fileName);
	}

	/**
	 * Создать временный файл из потока
	 * @param inputStream поток
	 * @param fileName имя файла
	 * @return файл в который записали
	 * @throws IOException
	 */
	public static File writeToTempDirectory(InputStream inputStream, String fileName) throws IOException
	{
		return write(inputStream, getTempDirectoryPath() + fileName);
	}

	/**
	 * Создать временный файл из массива байт
	 * @param data массив байт
	 * @param fileName имя файла
	 * @return файл в который записали
	 * @throws IOException
	 */
	public static File writeToTempDirectory(byte[] data, String fileName) throws IOException
	{
		return write(new ByteArrayInputStream(data), getTempDirectoryPath() + fileName);
	}

	/**
	 * прочитать временный файл в массив байт
	 * @param fileName имя файла
	 * @return данные из файла
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
	 * удалить временный файл
	 * @param fileName имя файла
	 * @return удалили или нет
	 * @throws IOException
	 */
	public static boolean deleteTempDirectoryFile(String fileName) throws IOException
	{
		return new File(getTempDirectoryPath().concat(fileName)).delete();
	}

	/**
	 * Создать каталог (если есть такая ничего не делает)
	 * @param pathName - путь
	 * @return созданная директория
	 */
	public static File createDirectory(String pathName)
	{
		File file = new File(pathName);
		if ( !file.exists() )
		{
			if (!file.mkdirs())
				throw new RuntimeException("Ошибка при создании каталога " + pathName);
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
				throw new RuntimeException("Ошибка чтения из файла");
			curPos += len;
		}
		return fullBuffer;
	}

	/**
	 * Удалить папку
	 * @param path - путь
	 * @throws IOException
	 */
	public static void deleteDirectory(String path) throws IOException
	{
		deleteDirectory(path, true);
	}

	/**
	 * Удалить папку
	 * @param path путь
	 * @param exceptionInError выбрасывать исключение при ошибке удаления.
	 * @throws IOException
	 */
	public static void deleteDirectory(String path, boolean exceptionInError) throws IOException
	{
		File file = new File(path);

		if ( !file.exists() && exceptionInError )
			throw new IOException("Файл " + path + " не существует");

		if (file.isDirectory())
		{
			File[] files = file.listFiles();
			for (int i=0; i<files.length; i++)
				deleteDirectory(files[i].getAbsolutePath(), exceptionInError);
		}

		if (!file.delete() && exceptionInError)
			throw new RuntimeException("Ошибка при удалении файла " + path);
	}

	/**
	 * Удалить файл, если он есть
	 * @param file - файл
	 * @throws IOException удалить не удалось
	 */
	public static void deleteFileIfExists(File file) throws IOException
	{
		if (!file.exists())
			return;

		if (!file.delete())
			throw new IOException("Не удалось удалить файл " + file.getAbsolutePath());
	}

	/**
	 * Преобразует путь в корректный для текущей ОС
	 * @param path - путь
	 * @return корректный путь
	 */
	public static String normalizePath(String path)
	{
		if (path == null)
			return path;

		return path.replace("\\", File.separator);
	}

	/**
	 * Преобразует пути ос, в пути для хранения
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
	 * Возвращает расширение файла
	 * @param file - файл
	 * @return расширение или null, если у файла нет расширения
	 */
	public static String getFileExtension(File file)
	{
		return getFileExtension(file.getName());
	}

	/**
	 * Возвращает расширение файла
	 * @param filename - имя файла
	 * @return расширение или null, если у файла нет расширения
	 */
	public static String getFileExtension(String filename)
	{
		if (StringHelper.isEmpty(filename))
			throw new IllegalArgumentException("Аргумент 'filename' не может быть пустым");

		int dot = filename.lastIndexOf('.');
		return (dot!=-1 && dot!=filename.length()-1) ? filename.substring(dot+1) : null;
	}

	/**
	 * Возвращает путь с файл сепаратором.
	 * @param path0 Путь
	 * @param fileName Имя файла
	 * @return Путь с файл сепаратором
	 */
	public static String getCurrentFilePath(String path0, String fileName) throws IOException
	{
		String path = new File(path0).getCanonicalPath();
		//определяем случай когда указан root в качестве папки,
		//чтобы не добавлять лишний сепаратор.
		String[] pathArr = path.split(":");
		if (!(StringHelper.equals(pathArr[pathArr.length - 1], File.separator)))
			return new File(path).getCanonicalPath() + File.separator + fileName;
		return path + fileName;
	}

	/**
	 * Тестирует файл на возможность записи
	 * @param file
	 * @return
	 */
	public static boolean canWrite(File file)
	{
		if (file != null)
		if (file.canWrite())
			return true;
		log.error("Нет прав на запись в файл "+file.getName());
		return false;
	}

	/**
	 *
	 * Загрузка набора свойств (пары ключ-значение) в объект Properties  
	 *
	 * @param file файл хранящий загружаемые свойства
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
	 * Сохранить набор свойств (пары ключ-значение) в файл.
	 * Записывает только в существующий файл! 
	 *
	 * @param file файл для записи
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