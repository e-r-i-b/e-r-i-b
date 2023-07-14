package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.footer;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Link;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * В отличии от другого подобного типа({@link com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.help.Help})
 * содержит только ссылку для редиректа и описание отображаемое пользователю
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
	 * @param link Справка («Помощь онлайн»)
	 */
	public FooterHelp(Link link)
	{
		this.link = link;
	}

	/**
	 * @return Справка («Помощь онлайн»)
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
