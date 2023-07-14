package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.MenuContainer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Описывает содержание визуального интерфейса
 *
 * @author Balovtsev
 * @since 25.04.14
 */
@XmlType(propOrder = {"status", "menuContainer"})
@XmlRootElement(name = "message")
public class MenuResponse extends Response
{
	private MenuContainer menuContainer;

	/**
	 */
	public MenuResponse()
	{
		super();
	}

	/**
	 * @param status статус ответа
	 * @param menuContainer контейнер меню
	 */
	public MenuResponse(Status status, MenuContainer menuContainer)
	{
		super(status);
		this.menuContainer = menuContainer;
	}

	/**
	 * Не обязателен в случае отсутствия входящих в его состав элементов или при наличии в ответе ошибок
	 *
	 * @return Контейнер меню
	 */
	@XmlElement(name = "menuContainer", required = false)
	public MenuContainer getMenuContainer()
	{
		return menuContainer;
	}

	public void setMenuContainer(MenuContainer menuContainer)
	{
		this.menuContainer = menuContainer;
	}
}
