package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.header;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.UrlXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * �������
 *
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlRootElement(name = "logotype")
public class Logotype
{
	private String link;

	/**
	 */
	public Logotype() {}

	/**
	 * @param link ������ ��� ��������� � ����
	 */
	public Logotype(String link)
	{
		this.link = link;
	}

	/**
	 * @return ������ ��� ��������� � ����
	 */
	@XmlJavaTypeAdapter(UrlXmlAdapter.class)
	@XmlElement(name = "link", required = true)
	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}
}
