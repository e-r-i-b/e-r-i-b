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
 * XML-писатель с выводом в строку
 */
public class XMLStringWriter extends XMLWriter
{
	private final StringWriter stringWriter = new StringWriter();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param outputFormat - формат xml-строки с результатом
	 */
	public XMLStringWriter(OutputFormat outputFormat)
	{
		construct(stringWriter, outputFormat);
	}

	/**
	 * @return xml-строка с результатом
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
