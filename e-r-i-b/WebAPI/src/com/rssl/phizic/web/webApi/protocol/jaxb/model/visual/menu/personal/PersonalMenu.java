package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.personal;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Link;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlType(propOrder = {"staticList", "dropDownList"})
@XmlRootElement(name = "personalMenu")
public class PersonalMenu
{
	private List<Link>        staticList;
	private DropDownContainer dropDownList;

	/**
	 */
	public PersonalMenu() {}

	/**
	 * @param staticList блок элементов ссылок
	 * @param dropDownList блок элементов выпадающих списков
	 */
	public PersonalMenu(List<Link> staticList, DropDownContainer dropDownList)
	{
		this.staticList   = staticList;
		this.dropDownList = dropDownList;
	}

	/**
	 * Блок элементов-ссылок. В блоке могут быть переданы элементы
	 * «История операций в Сбербанк Онлайн» и «Спасибо от Сбербанка»
	 *
	 * @return List&lt;Link&gt;
	 */
	@XmlElementWrapper(name = "staticLinksList", required = false)
	@XmlElement(name = "link", required = true)
	public List<Link> getStaticList()
	{
		return staticList;
	}

	/**
	 * Блок элементов выпадающих списков
	 *
	 * @return DropDownList
	 */
	@XmlElement(name = "dropDownLinksList", required = false)
	public DropDownContainer getDropDownList()
	{
		return dropDownList;
	}

	public void setStaticList(List<Link> staticList)
	{
		this.staticList = staticList;
	}

	public void setDropDownList(DropDownContainer dropDownList)
	{
		this.dropDownList = dropDownList;
	}
}
