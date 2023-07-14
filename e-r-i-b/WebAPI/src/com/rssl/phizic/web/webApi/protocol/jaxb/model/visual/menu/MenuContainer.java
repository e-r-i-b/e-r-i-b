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
 * ��������� ��������� ����������� ���������� ������������
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
	 * �������� ���������� � ������, ���� �� ��� �������� � �������.
	 * ������ ������� ����������� �� ������ ������������ ���� � ������� exclude=HEAD.
	 *
	 * @return ������� ����������� ���������� ������������. ���������
	 */
	@XmlElement(name = "header", required = false)
	public Header getHeader()
	{
		return header;
	}

	/**
	 * �������� ���������� � ������, ���� �� ��� �������� � �������.
	 * ������ ������� ����������� �� ������ ������������ ���� � ������� exclude=FOOTER
	 *
	 * @return ������� ����������� ���������� ������������. ������
	 */
	@XmlElement(name = "footer", required = false)
	public Footer getFooter()
	{
		return footer;
	}

	/**
	 * �������� ���������� � ������, ���� �� ��� �������� � �������.
	 * ������ ������� ����������� �� ������ ������������ ���� � ������� exclude=MAIN_MENU
	 *
	 * @return ������� ����������� ���������� ������������. ������� ����
	 */
	@XmlElement(name = "mainMenu", required = false)
	public MainMenu getMainMenu()
	{
		return mainMenu;
	}

	/**
	 * �������� ���������� � ������, ���� �� ��� �������� � �������.
	 * ������ ������� ����������� �� ������ ������������ ���� � ������� exclude=SETTINGS
	 *
	 * @return ������� ����������� ���������� ������������. ���������
	 */
	@XmlElement(name = "settings", required = false)
	public Settings getSettings()
	{
		return settings;
	}

	/**
	 * �������� ���������� � ������, ���� �� ��� �������� � �������.
	 * ������ ������� ����������� �� ������ ������������ ���� � ������� exclude=PERSONAL_MENU
	 *
	 * @return ������� ����������� ���������� ������������. ������ ����
	 */
	@XmlElement(name = "personalMenu", required = false)
	public PersonalMenu getPersonalMenu()
	{
		return personalMenu;
	}

	/**
	 * �������� ���������� � ������, ���� �� ��� �������� � �������.
	 * ������ ������� ����������� �� ������ ������������ ���� � ������� exclude=HELP
	 *
	 * @return ������� ����������� ���������� ������������. ������
	 */
	@XmlElement(name = "help", required = false)
	public Help getHelp()
	{
		return help;
	}

	/**
	 * @return �������������� ��������� � �������� ����������� ����������
	 * ����� � ��������� ���������� � ������� �������.
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
	 * @return ������� ����, ��� � ���������� ���� ���� �� ���-��
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
