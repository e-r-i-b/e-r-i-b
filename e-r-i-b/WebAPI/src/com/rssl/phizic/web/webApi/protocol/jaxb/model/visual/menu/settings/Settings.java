package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.settings;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Link;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlRootElement(name = "settings")
public class Settings
{
	private Link link;

	/**
	 */
	public Settings() {}

	/**
	 * @param link ������������ �������� ��� �����������
	 * ������������ � ������ ��� ��������� � ����
	 */
	public Settings(Link link)
	{
		this.link = link;
	}

	/**
	 * @return ������������ �������� ��� �����������
	 * ������������ � ������ ��� ��������� � ����
	 */
	@XmlElement(name = "link", required = true)
	public Link getLink()
	{
		return link;
	}

	public void setLink(Link link)
	{
		this.link = link;
	}
}
