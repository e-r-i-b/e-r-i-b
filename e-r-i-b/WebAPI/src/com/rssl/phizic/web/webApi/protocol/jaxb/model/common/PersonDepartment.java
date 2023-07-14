package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ������������� ������������
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
	 * @param id ������������� �������������
	 * @param name ������������ �������������
	 */
	public PersonDepartment(Long id, String name)
	{
		this.id   = id;
		this.name = name;
	}

	/**
	 * @return ������������� �������������
	 */
	@XmlElement(name = "id", required = true)
	public Long getId()
	{
		return id;
	}

	/**
	 * @return ������������ �������������
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
