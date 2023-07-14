package com.rssl.phizic.utils.xml;

import org.xml.sax.helpers.AttributesImpl;

/**
 * @author Erkin
 * @ created 18.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� ������ ��������� DOM-��������
 * ��������:
 * - ����������� ������������ ������ "�� ����" - ����� ����� ������� add.
 * ��������:
 *      writer.startElement("tagName", new XMLElementAttributes()
 *	            .add("attribute1", "value1")
 *              .add("attribute2", "value2")

 */
public class XMLElementAttributes extends AttributesImpl
{
	/**
	 * ����������� ��� ������� ������ ���������
	 */
	public XMLElementAttributes() {}

	/**
	 * ����������� ��� ������ �� ������ ��������
	 * @param name - ��� ��������
	 * @param value - �������� ��������
	 */
	public XMLElementAttributes(String name, String value)
	{
		add(name, value);
	}

	/**
	 * �������� �������
	 * @param name - ��� ��������
	 * @param value - �������� ��������
	 * @return this
	 */
	public XMLElementAttributes add(String name, String value)
	{
		addAttribute("", "", name, "CDATA", value);
		return this;
	}
}
