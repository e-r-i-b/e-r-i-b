package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ������� �������� ����
 *
 * @author Balovtsev
 * @since 05.05.2014
 */
@XmlType(propOrder = {"link", "type"})
@XmlRootElement(name = "menuElement")
public class MenuElement
{
	private Link link;
	private Type type;

	/**
	 */
	public MenuElement() {}

	/**
	 * @param link ������ ��� �������� � ������������ ������ ����
	 * @param type ��� ����
	 */
	public MenuElement(Link link, Type type)
	{
		this.link = link;
		this.type = type;
	}

	/**
	 * �������� ������������ ��� ����������� ������������, � ����� ������ ��� ��������� � ����
	 *
	 * @return Link
	 */
	@XmlElement(name = "link", required = true)
	public Link getLink()
	{
		return link;
	}

	/**
	 *
	 * @return ��� ��������
	 */
	@XmlElement(name = "type", required = true)
	public Type getType()
	{
		return type;
	}

	public void setLink(Link link)
	{
		this.link = link;
	}

	public void setType(Type type)
	{
		this.type = type;
	}
}
