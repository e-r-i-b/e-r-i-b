package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.help;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Link;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ������������ ��� ����������� ������������
 *
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlRootElement(name = "text")
public class HelpText
{
	private Link link;

	/**
	 */
	public HelpText() {}

	/**
	 * @param link ����� ��������� ������������ ��� ��������� ������� �� ����� � ����� � ������
	 * ��� ��������� � ���� ������ ����� ���������� ��������.
	 */
	public HelpText(Link link)
	{
		this.link = link;
	}

	/**
	 * @return ����� ��������� ������������ ��� ��������� ������� �� ����� � ����� � ������
	 * ��� ��������� � ���� ������ ����� ���������� ��������.
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
