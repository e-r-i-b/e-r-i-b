package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.personal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author fudzya
 * @since 01.05.14.
 */
@XmlTransient
public abstract class DropDownContainerElementWithTitle extends DropDownContainerElement
{
	private String title;

	/**
	 * @return Ќаименование дл€ отображени€ пользователю
	 */
	@XmlElement(name = "title", required = true)
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
}
