package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.header;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Link;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Обращение в службу помощи «Конверт»
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
	 * @param link текст подсказки, отображаемой при наведении курсора и ссылку для редиректа в ЕРИБ
	 * @param notification ярлык с количеством полученных уведомлений
	 */
	public Helpdesc(Link link, Notification notification)
	{
		this.link         = link;
		this.notification = notification;
	}

	/**
	 * @return Текст подсказки, отображаемой при наведении курсора и ссылку для редиректа в ЕРИБ
	 */
	@XmlElement(name = "link", required = true)
	public Link getLink()
	{
		return link;
	}

	/**
	 *
	 * @return Ярлык с количеством полученных уведомлений
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
