package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Элемент главного меню
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
	 * @param link ссылка для перехода и наименование пункта меню
	 * @param type тип меню
	 */
	public MenuElement(Link link, Type type)
	{
		this.link = link;
		this.type = type;
	}

	/**
	 * Содержит наименование для отображения пользователю, а также ссылку для редиректа в ЕРИБ
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
	 * @return Тип элемента
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
