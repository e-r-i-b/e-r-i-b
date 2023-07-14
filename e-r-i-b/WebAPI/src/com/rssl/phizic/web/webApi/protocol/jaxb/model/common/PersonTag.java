package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.UrlXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.XmlCalendarAdapter;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Описание клиента
 *
 * @author Balovtsev
 * @since  29.04.2014
 */
@XmlType(propOrder = {"surName", "firstName", "patrName", "loginId", "blockId", "linkFIO", "lastLogonDate", "lastIpAddress", "creationType", "department", "region"})
@XmlRootElement(name = "person")
public class PersonTag
{
	private Long             loginId;
	private String           surName;
	private String           firstName;
	private String           patrName;
	private String           linkFIO;
	private String           lastIpAddress;
	private CreationType     creationType;
	private PersonDepartment department;
	private PersonRegion     region;
	private Calendar         lastLogonDate;
	private Long             blockId;

	/**
	 */
	public PersonTag() {}

	/**
	 * @return id логина
	 */
	@XmlElement(name = "loginID", required = true)
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * @return Фамилия
	 */
	@XmlElement(name = "surName", required = true)
	public String getSurName()
	{
		return surName;
	}

	/**
	 * @return Имя
	 */
	@XmlElement(name = "firstName", required = true)
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @return Отчество
	 */
	@XmlElement(name = "patrName", required = true)
	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * @return Ссылка для редиректа в ЕРИБ при нажатии на ФИО
	 */
	@XmlJavaTypeAdapter(UrlXmlAdapter.class)
	@XmlElement(name = "linkFIO", required = true)
	public String getLinkFIO()
	{
		return linkFIO;
	}

	/**
	 * @return Тип подключение клиента
	 */
	@XmlElement(name = "creationType", required = true)
	public CreationType getCreationType()
	{
		return creationType;
	}

	/**
	 * @return Подразделение пользователя
	 */
	@XmlElement(name = "department", required = true)
	public PersonDepartment getDepartment()
	{
		return department;
	}

	/**
	 * @return Регион пользователя
	 */
	@XmlElement(name = "region", required = true)
	public PersonRegion getRegion()
	{
		return region;
	}

	/**
	 * @return IP адрес последнего входа
	 */
	@XmlElement(name = "lastIpAddress", required = false)
	public String getLastIpAddress()
	{
		return lastIpAddress;
	}

	/**
	 * @return Дата последнего входа
	 */
	@XmlJavaTypeAdapter(XmlCalendarAdapter.class)
	@XmlElement(name = "lastLogonDate", required = false)
	public Calendar getLastLogonDate()
	{
		return lastLogonDate;
	}

	/**
	 * @return Номер блока, в котором обслуживается клиент
	 */
	@XmlElement(name = "blockId", required = true)
	public Long getBlockId()
	{
		return blockId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public void setLinkFIO(String linkFIO)
	{
		this.linkFIO = linkFIO;
	}

	public void setLastIpAddress(String lastIpAddress)
	{
		this.lastIpAddress = lastIpAddress;
	}

	public void setCreationType(CreationType creationType)
	{
		this.creationType = creationType;
	}

	public void setDepartment(PersonDepartment department)
	{
		this.department = department;
	}

	public void setRegion(PersonRegion region)
	{
		this.region = region;
	}

	public void setLastLogonDate(Calendar lastLogonDate)
	{
		this.lastLogonDate = lastLogonDate;
	}

	public void setBlockId(Long blockId)
	{
		this.blockId = blockId;
	}
}
