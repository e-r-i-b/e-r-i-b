package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.header;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.PersonTag;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Элемент визуального интерфейса пользователя. Заголовок
 *
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlType(propOrder = {"logotype", "phones", "helpdesc", "avatar", "person", "exit"})
@XmlRootElement(name = "header")
public class Header
{
	private List<String> phones;
	private Avatar       avatar;
	private Logotype     logotype;
	private PersonTag    person;
	private Helpdesc     helpdesc;
	private Exit         exit;

	/**
	 * @return Логотип
	 */
	@XmlElement(name = "logotype", required = false)
	public Logotype getLogotype()
	{
		return logotype;
	}

	/**
	 * @return Аватар
	 */
	@XmlElement(name = "avatar", required = false)
	public Avatar getAvatar()
	{
		return avatar;
	}

	/**
	 * @return Выход
	 */
	@XmlElement(name = "exit", required = false)
	public Exit getExit()
	{
		return exit;
	}

	/**
	 * @return Контейнер телефонов
	 */
	@XmlElementWrapper(name = "phones", required = false)
	@XmlElement(name = "phone", required = true)
	public List<String> getPhones()
	{
		return phones;
	}

	/**
	 * @return Информация о клиенте
	 */
	@XmlElement(name = "person", required = false)
	public PersonTag getPerson()
	{
		return person;
	}

	/**
	 * @return Обращение в службу помощи «Конверт»
	 */
	@XmlElement(name = "helpdesc", required = false)
	public Helpdesc getHelpdesc()
	{
		return helpdesc;
	}

	public void setLogotype(Logotype logotype)
	{
		this.logotype = logotype;
	}

	public void setAvatar(Avatar avatar)
	{
		this.avatar = avatar;
	}

	public void setExit(Exit exit)
	{
		this.exit = exit;
	}

	public void setPhones(List<String> phones)
	{
		this.phones = phones;
	}

	public void setPerson(PersonTag person)
	{
		this.person = person;
	}

	public void setHelpdesc(Helpdesc helpdesc)
	{
		this.helpdesc = helpdesc;
	}
}
