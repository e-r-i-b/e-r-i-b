package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.fastpaymentpanel;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.UrlXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Picture;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Описывает элемент содержащий данные о поставщике, используемые для панели быстрой оплаты
 * Xml-представление данного класса имеет вид:
 *
 * <pre>
 * {@code
 *	<supplier>
 *	    <id>3</id>
 *	    <picture>
 *	        <link>string</link>
 *	        <url>string</url>
 *	    </picture>
 *	    <title>string</title>
 *	</supplier>
 * }
 * </pre>
 *
 * @author Balovtsev
 * @since 22.04.14
 */
@XmlType(propOrder = {"id", "title", "picture", "url"})
@XmlRootElement(name = "supplier")
public class Supplier
{
	private int id;
	private String title;
	private Picture picture;
	private String url;

	/**
	 * @return Идентификатор поставщика
	 */
	@XmlElement(name = "id", required = true)
	public int getId()
	{
		return id;
	}

	/**
	 * @return Название поставщика
	 */
	@XmlElement(name = "title", required = true)
	public String getTitle()
	{
		return title;
	}

	/**
	 * @return Изображение для поставщика
	 */
	@XmlElement(name = "picture", required = true)
	public Picture getPicture()
	{
		return picture;
	}

	/**
	 * @return Ссылка для оплаты
	 */
	@XmlJavaTypeAdapter(UrlXmlAdapter.class)
	@XmlElement(name = "url", required = true)
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setPicture(Picture picture)
	{
		this.picture = picture;
	}
}
