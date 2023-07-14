package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.Tab;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Ответ, содержащий наполнение страницы "Мои финансы"
 * @author Pankin
 * @ created 11.07.14
 * @ $Author$
 * @ $Revision$
 */
@XmlType(propOrder = {"status", "description", "tabs"})
@XmlRootElement(name = "message")
public class ALFFinanceContentResponse extends Response
{
	private String description;
	private List<Tab> tabs;

	@XmlElement(name = "description", required = false)
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@XmlElementWrapper(name = "tabs", required = false)
	@XmlElement(name = "tab", required = true)
	public List<Tab> getTabs()
	{
		return tabs;
	}

	public void setTabs(List<Tab> tabs)
	{
		this.tabs = tabs;
	}
}
