package com.rssl.phizic.utils.xml;

/**
 * @author Erkin
 * @ created 19.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������� XMLStringWriter ��� ������������ XML-��������� ������ ����� ���������
 */
public class XMLMessageWriter extends XMLStringWriter
{
	/**
	 * ctor
	 * @param encoding - ���������
	 */
	public XMLMessageWriter(String encoding)
	{
		super(XMLOutputFormats.getCompactXMLFormat(encoding));
	}
}
