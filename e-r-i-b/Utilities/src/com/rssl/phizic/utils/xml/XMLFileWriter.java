package com.rssl.phizic.utils.xml;

import org.dom4j.io.OutputFormat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author Erkin
 * @ created 17.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Файловый XML-писатель
 */
public class XMLFileWriter extends XMLWriter
{
	/**
	 * ctor
	 * @param file - путь к xml-файлу
	 * @param outputFormat - формат xml-файла
	 */
	public XMLFileWriter(File file, OutputFormat outputFormat)
	{
		String encoding = outputFormat.getEncoding();
		try
		{
			// noinspection IOResourceOpenedButNotSafelyClosed
			PrintWriter printWriter = new PrintWriter(file, encoding);
			construct(printWriter, outputFormat);
		}
		catch (FileNotFoundException e)
		{
			throw new IllegalArgumentException("Не найден файл " + file, e);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new IllegalArgumentException("Не поддерживается кодировка " + encoding, e);
		}
	}
}
