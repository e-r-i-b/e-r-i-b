package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Регион пользователя
 *
 * @author Balovtsev
 * @since 30.04.2014
 */

@XmlType(propOrder = {"id", "name"})
@XmlRootElement(name = "region")
public class PersonRegion
{
	private Long    id;
	private String name;

	/**
	 */
	public PersonRegion() {}

	/**
	 * @param id идентификатор региона
	 * @param name наименование региона
	 */
	public PersonRegion(Long id, String name)
	{
		this.id   = id;
		this.name = name;
	}

	/**
	 * @return идентификатор региона
	 */
	@XmlElement(name = "id", required = true)
	public Long getId()
	{
		return id;
	}

	/**
	 * @return наименование региона
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
