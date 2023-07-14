package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Подразделение пользователя
 *
 * @author Balovtsev
 * @since 30.04.2014
 */

@XmlType(propOrder = {"id", "name"})
@XmlRootElement(name = "department")
public class PersonDepartment
{
	private Long    id;
	private String name;

	/**
	 */
	public PersonDepartment() {}

	/**
	 *
	 * @param id идентификатор подразделения
	 * @param name наименование подразделения
	 */
	public PersonDepartment(Long id, String name)
	{
		this.id   = id;
		this.name = name;
	}

	/**
	 * @return Идентификатор подразделения
	 */
	@XmlElement(name = "id", required = true)
	public Long getId()
	{
		return id;
	}

	/**
	 * @return Наименование подразделения
	 */
	@XmlElement(name = "name", required = true)
	public String getName()
	{
		return name;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
