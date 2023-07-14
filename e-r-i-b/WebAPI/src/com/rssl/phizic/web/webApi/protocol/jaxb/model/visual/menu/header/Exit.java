package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.header;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Link;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Выход
 *
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlRootElement(name = "exit")
public class Exit
{
	private Link link;

	/**
	 */
	public Exit() {}

	/**
	 * @param link текст подсказки, отображаемой при наведении курсора и ссылка для редиректа в ЕРИБ
	 */
	public Exit(Link link)
	{
		this.link = link;
	}

	/**
	 * @return Содержит текст подсказки, отображаемой при наведении курсора и ссылку для редиректа в ЕРИБ
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
