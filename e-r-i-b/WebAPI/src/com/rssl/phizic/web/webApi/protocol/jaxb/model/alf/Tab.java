package com.rssl.phizic.web.webApi.protocol.jaxb.model.alf;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.UrlXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Вкладка на странице "Мои финансы"
 * @author Pankin
 * @ created 11.07.14
 * @ $Author$
 * @ $Revision$
 */
@XmlType(propOrder = {"position", "name", "url"})
@XmlRootElement(name = "tab")
public class Tab
{
	private int position;
	private String name;
	private String url;

	public Tab()
	{
	}

	public Tab(int position, String name, String url)
	{
		this.position = position;
		this.name = name;
		this.url = url;
	}

	@XmlElement(name = "position", required = true)
	public int getPosition()
	{
		return position;
	}

	public void setPosition(int position)
	{
		this.position = position;
	}

	@XmlElement(name = "name", required = true)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@XmlJavaTypeAdapter(UrlXmlAdapter.class)
	@XmlElement(name = "url", required = false)
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}
