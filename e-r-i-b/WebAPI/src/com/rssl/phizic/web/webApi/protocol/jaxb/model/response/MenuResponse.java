package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.MenuContainer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ��������� ���������� ����������� ����������
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
	 * @param status ������ ������
	 * @param menuContainer ��������� ����
	 */
	public MenuResponse(Status status, MenuContainer menuContainer)
	{
		super(status);
		this.menuContainer = menuContainer;
	}

	/**
	 * �� ���������� � ������ ���������� �������� � ��� ������ ��������� ��� ��� ������� � ������ ������
	 *
	 * @return ��������� ����
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
