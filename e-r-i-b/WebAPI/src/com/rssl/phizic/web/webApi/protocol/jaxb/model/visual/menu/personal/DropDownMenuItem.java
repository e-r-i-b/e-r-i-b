package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.personal;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Link;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MenuElement;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Ёлемент личного меню
 * @author Pankin
 * @ created 16.06.14
 * @ $Author$
 * @ $Revision$
 */
@XmlType(propOrder = {"menu", "links", "commands"})
@XmlRootElement(name = "dropDownMenuItem")
public class DropDownMenuItem
{
	private MenuElement menu;
	private List<Link> links;
	private List<Link> commands;

	@XmlElement(name = "menuElement", required = true)
	public MenuElement getMenu()
	{
		return menu;
	}

	public void setMenu(MenuElement menu)
	{
		this.menu = menu;
	}

	@XmlElementWrapper(name = "linksContainer", required = false)
	@XmlElement(name = "link", required = false)
	public List<Link> getLinks()
	{
		return links;
	}

	public void setLinks(List<Link> links)
	{
		this.links = links;
	}

	@XmlElementWrapper(name = "commandsContainer", required = false)
	@XmlElement(name = "link", required = false)
	public List<Link> getCommands()
	{
		return commands;
	}

	public void setCommands(List<Link> commands)
	{
		this.commands = commands;
	}
}
