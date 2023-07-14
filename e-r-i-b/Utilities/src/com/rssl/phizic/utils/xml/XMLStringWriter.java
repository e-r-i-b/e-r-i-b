package com.rssl.phizic.utils.xml;

import org.dom4j.io.OutputFormat;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Erkin
 * @ created 17.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * XML-�������� � ������� � ������
 */
public class XMLStringWriter extends XMLWriter
{
	private final StringWriter stringWriter = new StringWriter();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param outputFormat - ������ xml-������ � �����������
	 */
	public XMLStringWriter(OutputFormat outputFormat)
	{
		construct(stringWriter, outputFormat);
	}

	/**
	 * @return xml-������ � �����������
	 */
	@Override
	public String toString()
	{
		try
		{
			flush();
			return stringWriter.toString();
		}
		catch (IOException e)
		{
			throw new WriteXMLException(e);
		}
	}
}
