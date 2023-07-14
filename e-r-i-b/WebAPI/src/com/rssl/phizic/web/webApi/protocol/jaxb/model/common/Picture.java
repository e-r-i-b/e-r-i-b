package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.UrlXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Описывает элемент картинка.
 *
 * Xml-представление данного класса имеет вид:
 * <pre>
 * {@code
 *	<picture>
 *	    <link>string</link>
 *	    <url>string</url>
 *	</picture>
 * }
 * </pre>
 *
 * @author Balovtsev
 * @since 22.04.14
 */
@XmlType(propOrder = {"url", "link"})
@XmlRootElement(name="picture")
public class Picture
{
	private String url;
	private String link;

	/**
	 *
	 */
	public Picture() {}

	/**
	 *
	 * @param url  URL картинки
	 * @param link ссылка для редиректа
	 */
	public Picture(String url, String link)
	{
		this.url = url;
		this.link = link;
	}

	/**
	 * @return URL-адрес изображения
	 */
	@XmlJavaTypeAdapter(UrlXmlAdapter.class)
	@XmlElement(name = "url", required = true)
	public String getUrl()
	{
		return url;
	}

	/**
	 * @return ссылка для редиректа в ЕРИБ
	 */
	@XmlJavaTypeAdapter(UrlXmlAdapter.class)
	@XmlElement(name = "link", required = true)
	public String getLink()
	{
		return link;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public void setLink(String link)
	{
		this.link = link;
	}
}
