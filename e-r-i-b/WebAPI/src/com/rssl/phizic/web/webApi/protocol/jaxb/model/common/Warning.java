package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ������� ������� ���� ��������� ������� warning
 *
 * Xml-������������� ������� ������ ����� ���:
 *
 * <pre>
 * {@code
 *  <warning>
 *	    <text>string</text>
 *		<elementId>string</elementId><!--�������������� �������-->
 *	</warning>
 * }
 * </pre>
 *
 * @see Status
 * @author Balovtsev
 * @since 22.04.14
 */
@XmlType(propOrder = {"text", "elementId"})
@XmlRootElement(name = "warning")
public class Warning
{
	private String text;
	private String elementId;

	/**
	 */
	public Warning() {}

	/**
	 * @param text
	 * @param elementId
	 */
	public Warning(String text, String elementId)
	{
		this.text = text;
		this.elementId = elementId;
	}

	/**
	 * @return ����� ��������� ����������� � CDATA. ����� ����� html ���� ��������������.
	 */
	@XmlElement(name = "text", required = true)
	public String getText()
	{
		return text;
	}

	/**
	 * @return ��� �������� ���������, � �������� ��������� ���������.
	 */
	@XmlElement(name = "elementId", required = false)
	public String getElementId()
	{
		return elementId;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public void setElementId(String elementId)
	{
		this.elementId = elementId;
	}
}
