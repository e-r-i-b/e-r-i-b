package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.footer;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Link;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * � ������� �� ������� ��������� ����({@link com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.help.Help})
 * �������� ������ ������ ��� ��������� � �������� ������������ ������������
 *
 * @author Balovtsev
 * @since  28.07.2014
 */
@XmlRootElement(name = "help")
public class FooterHelp
{
	private Link link;

	/**
	 */
	public FooterHelp() {}

	/**
	 * @param link ������� (������� ������)
	 */
	public FooterHelp(Link link)
	{
		this.link = link;
	}

	/**
	 * @return ������� (������� ������)
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
