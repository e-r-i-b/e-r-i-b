package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.footer;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.UrlXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * —оц. сеть
 *
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlType(propOrder = {"pictureUrl", "link"})
@XmlRootElement(name = "network")
public class Network
{
	private String link;
	private String pictureUrl;

	/**
	 */
	public Network() {}

	/**
	 * @param link ссылка дл€ редиректа на ресурс
	 * @param pictureUrl ссылка на изображение иконки соц. сети
	 */
	public Network(String link, String pictureUrl)
	{
		this.link       = link;
		this.pictureUrl = pictureUrl;
	}

	/**
	 * @return —сылка дл€ редиректа на ресурс
	 */
	@XmlJavaTypeAdapter(UrlXmlAdapter.class)
	@XmlElement(name = "link", required = true)
	public String getLink()
	{
		return link;
	}


	/**
	 * @return —сылка на изображение иконки соц. сети
	 */
	@XmlJavaTypeAdapter(UrlXmlAdapter.class)
	@XmlElement(name = "pictureUrl", required = true)
	public String getPictureUrl()
	{
		return pictureUrl;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public void setPictureUrl(String pictureUrl)
	{
		this.pictureUrl = pictureUrl;
	}
}
