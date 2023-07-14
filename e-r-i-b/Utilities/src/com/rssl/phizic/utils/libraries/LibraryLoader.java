package com.rssl.phizic.utils.libraries;

import com.rssl.phizic.utils.resources.ResourceHelper;
import com.rssl.phizic.utils.zip.ZipHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Roshka
 * @ created 17.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class LibraryLoader
{
	private static final Log log = LogFactory.getLog(LibraryLoader.class);

	/**
	 * Загрузить dll из ресурса
	 * @param libraryName
	 */
	public static void loadLibraryFromResources(String libraryName)
	{
		try
		{
			String tmpPath = System.getProperty("java.io.tmpdir");

			ResourceHelper.saveFileFromResource(libraryName, tmpPath, true);

			System.load(tmpPath + libraryName);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Ошибка при загрузке библиотеки", e);
		}
	}

	/**
	 * Загрузить dll из zip ресурса
	 *
	 * @param libraryName
	 */
	public static void loadLibraryFromZipResource(String zipResourceName, String libraryName)
	{
		try
		{
			String tmpPath = System.getProperty("java.io.tmpdir");

			try
			{
				log.trace("Stating unpack resource " + zipResourceName + " ...");
			    ZipHelper.unpackResource(zipResourceName, tmpPath);
				log.trace("Resource unpacked successfully " + zipResourceName);
			}
			catch(FileNotFoundException fne)
			{
				//давим если такой файл уже есть, скорее всего он занят.
				if( !new File(tmpPath + libraryName).exists() )
				{
					throw fne;
				}
				else
				{
					log.trace("Resource in use " + zipResourceName);
				}
			}
			System.load(tmpPath + libraryName);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Ошибка при загрузке библиотеки", e);
		}
	}

	/**
	 * Скопировать файл из ресурсов tmp директорию
	 *
	 * @param resourceName - имя файла
	 * @param saveDirPrefix - директория, относительно директории по умолчанию: default/saveDirPrefix/resourceName
	 * @return путь, по которому было сохранено
	 */
	public static File saveResourceToTmp(String resourceName, String saveDirPrefix)
	{
		String tmpPath = System.getProperty("java.io.tmpdir") + saveDirPrefix + File.separator;
		log.trace("Stating search resource " + resourceName + " ...");
	    ResourceHelper.saveFileFromResource(resourceName, tmpPath, true);
		log.trace("Resource finded " + resourceName);
		return new File(tmpPath);
	}

}