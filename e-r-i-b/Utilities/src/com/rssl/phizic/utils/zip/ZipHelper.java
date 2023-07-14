package com.rssl.phizic.utils.zip;

import com.rssl.phizic.utils.files.FileHelper;
import com.rssl.phizic.utils.resources.ResourceHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Roshka
 * @ created 22.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class ZipHelper
{
	/**
	 * Распаковать архив
	 *
	 * @param zipResourceName - имя zip архива в ресурсах
	 * @param targetDir       - куда распаковывать
	 */
	public static void unpackResource(String zipResourceName, String targetDir) throws IOException
	{
		unpack(ResourceHelper.loadResourceAsStream(zipResourceName), targetDir);
	}

	/**
	 * Распаковать архив
	 *
	 * @param source    - входной поток сжатых данных
	 * @param targetDir - куда распаковывать
	 */
	public static void unpack(InputStream source, String targetDir) throws IOException
	{
		ZipInputStream zipInputStream = null;
		try
		{
			zipInputStream = new ZipInputStream(source);

			// loop for each entry
			ZipEntry entry;
			while ((entry = zipInputStream.getNextEntry()) != null)
			{
				String elementName = entry.getName();
				StringBuffer oldChars = new StringBuffer();
				oldChars.append("/");
				StringBuffer newChars = new StringBuffer();
				newChars.append("\\");
				String path = targetDir + elementName.replace(oldChars, newChars);

				if (entry.isDirectory())
					FileHelper.createDirectory(path);
				else
					FileHelper.write( zipInputStream, path );
			}
		}
		finally
		{
			if (zipInputStream != null)
				zipInputStream.close();
		}
	}
}