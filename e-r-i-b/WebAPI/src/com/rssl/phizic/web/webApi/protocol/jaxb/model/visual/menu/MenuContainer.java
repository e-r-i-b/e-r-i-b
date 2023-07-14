package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.footer.Footer;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.header.Header;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.help.Help;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.main.MainMenu;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.personal.PersonalMenu;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.settings.Settings;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Контейнер элементов визуального интерфейса пользователя
 *
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlType(propOrder = {"header", "footer", "mainMenu", "settings", "personalMenu", "help", "mbMessage"})
@XmlRootElement(name = "menuContainer")
public class MenuContainer
{
	private Header                      header;
	private Footer                      footer;
	private MainMenu                    mainMenu;
	private Settings                    settings;
	private PersonalMenu                personalMenu;
	private Help                        help;
	private MBFunctionalTransferMessage mbMessage;

	/**
	 * Параметр обязателен в случае, если не был исключен в запросе.
	 * Данный элемент исключается из списка возвращаемых если в запросе exclude=HEAD.
	 *
	 * @return Элемент визуального интерфейса пользователя. Заголовок
	 */
	@XmlElement(name = "header", required = false)
	public Header getHeader()
	{
		return header;
	}

	/**
	 * Параметр обязателен в случае, если не был исключен в запросе.
	 * Данный элемент исключается из списка возвращаемых если в запросе exclude=FOOTER
	 *
	 * @return Элемент визуального интерфейса пользователя. Подвал
	 */
	@XmlElement(name = "footer", required = false)
	public Footer getFooter()
	{
		return footer;
	}

	/**
	 * Параметр обязателен в случае, если не был исключен в запросе.
	 * Данный элемент исключается из списка возвращаемых если в запросе exclude=MAIN_MENU
	 *
	 * @return Элемент визуального интерфейса пользователя. Главное меню
	 */
	@XmlElement(name = "mainMenu", required = false)
	public MainMenu getMainMenu()
	{
		return mainMenu;
	}

	/**
	 * Параметр обязателен в случае, если не был исключен в запросе.
	 * Данный элемент исключается из списка возвращаемых если в запросе exclude=SETTINGS
	 *
	 * @return Элемент визуального интерфейса пользователя. Настройки
	 */
	@XmlElement(name = "settings", required = false)
	public Settings getSettings()
	{
		return settings;
	}

	/**
	 * Параметр обязателен в случае, если не был исключен в запросе.
	 * Данный элемент исключается из списка возвращаемых если в запросе exclude=PERSONAL_MENU
	 *
	 * @return Элемент визуального интерфейса пользователя. Личное меню
	 */
	@XmlElement(name = "personalMenu", required = false)
	public PersonalMenu getPersonalMenu()
	{
		return personalMenu;
	}

	/**
	 * Параметр обязателен в случае, если не был исключен в запросе.
	 * Данный элемент исключается из списка возвращаемых если в запросе exclude=HELP
	 *
	 * @return Элемент визуального интерфейса пользователя. Помощь
	 */
	@XmlElement(name = "help", required = false)
	public Help getHelp()
	{
		return help;
	}

	/**
	 * @return Информационное сообщение о переносе функционала мобильного
	 * банка и мобильных приложений в профиль клиента.
	 */
	@XmlElement(name = "mbFunctionalTransferMessage", required = false)
	public MBFunctionalTransferMessage getMbMessage()
	{
		return mbMessage;
	}

	public void setHeader(Header header)
	{
		this.header = header;
	}

	public void setFooter(Footer footer)
	{
		this.footer = footer;
	}

	public void setMainMenu(MainMenu mainMenu)
	{
		this.mainMenu = mainMenu;
	}

	public void setSettings(Settings settings)
	{
		this.settings = settings;
	}

	public void setPersonalMenu(PersonalMenu personalMenu)
	{
		this.personalMenu = personalMenu;
	}

	public void setHelp(Help help)
	{
		this.help = help;
	}

	public void setMbMessage(MBFunctionalTransferMessage mbMessage)
	{
		this.mbMessage = mbMessage;
	}

	/**
	 * @return Признак того, что в контейнере есть хотя бы что-то
	 */
	@XmlTransient
	public boolean isComplete()
	{
		return !(header       == null &&
				 footer       == null &&
				 mainMenu     == null &&
				 settings     == null &&
				 personalMenu == null &&
				 help         == null &&
				 mbMessage    == null);
	}
}
