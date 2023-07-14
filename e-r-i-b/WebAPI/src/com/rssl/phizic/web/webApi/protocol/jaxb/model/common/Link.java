package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.UrlXmlAdapter;

import java.util.List;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Описывает элемент ссылка (link).
 *
 * Xml-представление данного класса имеет вид:
 * <pre>
 * {@code
 *	<link>
 *	    <title>string</title>
 *	    <url>string</url>
 *	</link>
 * }
 * </pre>
 *
 * @author Balovtsev
 * @since 22.04.14
 */
@XmlType(propOrder = {"title", "url", "tooltip", "properties"})
@XmlRootElement(name = "link")
public class Link
{
	private String url;
	private String title;
	private String tooltip;
	private List<Property> properties;

	/**
	 */
	public Link() {}

	/**
	 * @param url url перехода
	 * @param title наименование отображаемое пользователю
	 */
	public Link(String url, String title)
	{
		this.url   = url;
		this.title = title;
	}

	/**
	 * @param url url перехода
	 * @param title наименование отображаемое пользователю
	 * @param tooltip всплывающая подсказка
	 */
	public Link(String url, String title, String tooltip)
	{
		this.url = url;
		this.title = title;
		this.tooltip = tooltip;
	}

	/**
	 * @return наименование отображаемое пользователю
	 */
	@XmlElement(name = "title", required = true)
	public String getTitle()
	{
		return title;
	}

	/**
	 * @return url перехода
	 */
	@XmlJavaTypeAdapter(UrlXmlAdapter.class)
	@XmlElement(name = "url", required = false)
	public String getUrl()
	{
		return url;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	@XmlElementWrapper(name = "properties", required = false)
	@XmlElement(name = "property", required = false)
	public List<Property> getProperties()
	{
		return properties;
	}

	public void setProperties(List<Property> properties)
	{
		this.properties = properties;
	}

	@XmlElement(name = "tooltip", required = false)
	public String getTooltip()
	{
		return tooltip;
	}

	public void setTooltip(String tooltip)
	{
		this.tooltip = tooltip;
	}
}
