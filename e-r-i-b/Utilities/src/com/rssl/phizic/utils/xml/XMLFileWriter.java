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
 * �������� XML-��������
 */
public class XMLFileWriter extends XMLWriter
{
	/**
	 * ctor
	 * @param file - ���� � xml-�����
	 * @param outputFormat - ������ xml-�����
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
			throw new IllegalArgumentException("�� ������ ���� " + file, e);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new IllegalArgumentException("�� �������������� ��������� " + encoding, e);
		}
	}
}
