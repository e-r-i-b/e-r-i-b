package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.header;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Link;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ��������� � ������ ������ ��������
 *
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlType(propOrder = {"link", "notification"})
@XmlRootElement(name = "helpdesc")
public class Helpdesc
{
	private Link         link;
	private Notification notification;

	public Helpdesc() {}

	/**
	 * @param link ����� ���������, ������������ ��� ��������� ������� � ������ ��� ��������� � ����
	 * @param notification ����� � ����������� ���������� �����������
	 */
	public Helpdesc(Link link, Notification notification)
	{
		this.link         = link;
		this.notification = notification;
	}

	/**
	 * @return ����� ���������, ������������ ��� ��������� ������� � ������ ��� ��������� � ����
	 */
	@XmlElement(name = "link", required = true)
	public Link getLink()
	{
		return link;
	}

	/**
	 *
	 * @return ����� � ����������� ���������� �����������
	 */
	@XmlElement(name = "notification", required = true)
	public Notification getNotification()
	{
		return notification;
	}

	public void setLink(Link link)
	{
		this.link = link;
	}

	public void setNotification(Notification notification)
	{
		this.notification = notification;
	}
}
