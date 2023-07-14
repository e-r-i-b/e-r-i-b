package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.help;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Link;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ����� � ����� Help
 *
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlRootElement(name = "title")
public class HelpTitle
{
	private Link link;

	/**
	 */
	public HelpTitle() {}

	/**
	 * @param link ����� ��������� ������������ ��� ��������� �������
	 * �� �������� � ������ ��� ��������� � ���� ������ �������
	 *
	 */
	public HelpTitle(Link link)
	{
		this.link = link;
	}

	/**
	 * ���������� ����� ��������� ������������ ��� ��������� �������
	 * �� �������� � ������ ��� ��������� � ���� ������ �������
	 *
	 * @return Link
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
