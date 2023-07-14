package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.help;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Link;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Текст в блоке Help
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
	 * @param link текст подсказки отображаемый при наведении курсора
	 * на название и ссылку для редиректа в ЕРИБ раздел справки
	 *
	 */
	public HelpTitle(Link link)
	{
		this.link = link;
	}

	/**
	 * Возвращает текст подсказки отображаемый при наведении курсора
	 * на название и ссылку для редиректа в ЕРИБ раздел справки
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
