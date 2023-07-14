package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.personal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author fudzya
 * @since 01.05.14.
 */
@XmlTransient
public abstract class DropDownContainerElement
{
	private boolean hidden;

	/**
	 * —осто€ние выпадающего списка
	 *
	 * @return true - свернут, false - развернут
	 */
	@XmlElement(name = "hidden", required = true)
	public boolean isHidden()
	{
		return hidden;
	}

	public void setHidden(boolean hidden)
	{
		this.hidden = hidden;
	}
}
