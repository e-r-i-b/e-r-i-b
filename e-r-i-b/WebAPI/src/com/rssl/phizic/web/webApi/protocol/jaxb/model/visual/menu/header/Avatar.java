package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.header;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.UrlXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * ������
 *
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlRootElement(name = "avatar")
public class Avatar
{
	private String url;

	/**
	 */
	public Avatar() {}

	/**
	 * @param url URL-����� ��� ����������� �������
	 */
	public Avatar(String url)
	{
		this.url = url;
	}

	/**
	 *
	 * @return URL-����� ��� ����������� �������
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
}
