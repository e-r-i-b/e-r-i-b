package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.personal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlType(propOrder = {"title", "hidden", "service"})
@XmlRootElement(name = "autopayments")
public class AutoPayments extends DropDownContainerElementWithTitle
{
	private String service = "menucontainer.content.autopayments";

	/**
	 * @return Ќазвание сервиса дл€ получени€ автоплатежей
	 */
	@XmlElement(name = "service", defaultValue = "menucontainer.content.autopayments", required = true)
	public String getService()
	{
		return service;
	}

	public void setService(String service)
	{
		this.service = service;
	}
}
