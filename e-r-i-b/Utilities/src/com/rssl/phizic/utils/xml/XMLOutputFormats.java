package com.rssl.phizic.utils.xml;

import org.dom4j.io.OutputFormat;

/**
 * @author Erkin
 * @ created 19.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ���������� �������������� XML-����������
 */
public class XMLOutputFormats
{
	/**
	 * @param encoding - ���������
	 * @return ������ ������� ������������������ XML-��������� � �������� ���������
	 */
	public static OutputFormat getCompactXMLFormat(String encoding)
	{
		OutputFormat outputFormat = OutputFormat.createCompactFormat();
		outputFormat.setEncoding(encoding);
		return outputFormat;
	}

	/**
	 * @param encoding - ���������
	 * @return ������ ��������� ���������������� XML-��������� � �������� ���������
	 */
	public static OutputFormat getPrettyXMLFormat(String encoding)
	{
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		outputFormat.setEncoding(encoding);
		return outputFormat;
	}
}
