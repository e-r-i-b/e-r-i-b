package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.main;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MenuElement;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Type;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlType(propOrder = {"selectedType", "main", "others"})
@XmlRootElement(name = "mainMenu")
public class MainMenu
{
	private Type selectedType;
	private List<MenuElement> main;
	private List<MenuElement> others;

	/**
	 */
	public MainMenu() {}

	/**
	 * @param selectedType выбранный пункт меню
	 * @param main список пунктов меню отображаемых пользователю
	 * @param others список пунктов меню в вкладке "Прочее"
	 */
	public MainMenu(Type selectedType, List<MenuElement> main, List<MenuElement> others)
	{
		this.selectedType = selectedType;
		this.main = main;
		this.others = others;
	}

	/**
	 * @return Тип выбранного пункта меню
	 */
	@XmlElement(name = "selectedType", required = true)
	public Type getSelectedType()
	{
		return selectedType;
	}

	/**
	 * Контейнер основного списка. В данном списке обязательно возвращаются 2 элемента – один
	 * имеет тип MAIN, второй имеет тип PAYMENTS
	 *
	 * @return List&lt;MenuElement&gt;
	 */
	@XmlElementWrapper(name = "main", required = true)
	@XmlElement(name = "menuElement", required = true)
	public List<MenuElement> getMain()
	{
		return main;
	}

	/**
	 * Контейнер списка «Прочее». Контейнер не может содержать элементы типов MAIN и PAYMENTS
	 *
	 * @return List&lt;MenuElement&gt;
	 */
	@XmlElementWrapper(name = "others", required = false)
	@XmlElement(name = "menuElement", required = true)
	public List<MenuElement> getOthers()
	{
		return others;
	}

	public void setSelectedType(Type selectedType)
	{
		this.selectedType = selectedType;
	}

	public void setMain(List<MenuElement> main)
	{
		this.main = main;
	}

	public void setOthers(List<MenuElement> others)
	{
		this.others = others;
	}
}
