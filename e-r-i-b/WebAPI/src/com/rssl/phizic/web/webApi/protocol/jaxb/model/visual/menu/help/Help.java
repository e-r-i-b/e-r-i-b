package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.help;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlType(propOrder = {"title", "text"})
@XmlRootElement(name = "help")
public class Help
{
	private HelpTitle title;
	private HelpText  text;

	/**
	 */
	public Help() {}

	/**
	 * @param title наименование для отображения пользователю
	 * @param text текст в блоке
	 */
	public Help(HelpTitle title, HelpText text)
	{
		this.title = title;
		this.text  = text;
	}

	/**
	 * Содержит текст подсказки при наведении курсора на название и
	 * ссылку для редиректа в ЕРИБ раздел справки
	 *
	 * @return Наименование для отображения пользователю
	 */
	@XmlElement(name = "title", required = true)
	public HelpTitle getTitle()
	{
		return title;
	}

	/**
	 * Содержит текст подсказки при наведении курсора на текст в блоке и
	 * ссылку для редиректа в ЕРИБ раздел часто задаваемых вопросов
	 *
	 * @return Текст в блоке
	 */
	@XmlElement(name = "text", required = true)
	public HelpText getText()
	{
		return text;
	}

	public void setTitle(HelpTitle title)
	{
		this.title = title;
	}

	public void setText(HelpText text)
	{
		this.text = text;
	}
}
