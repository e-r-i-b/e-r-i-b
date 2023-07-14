package com.rssl.phizic.web.webApi.protocol.jaxb.model.alf;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.CDATAXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Категория операции
 *
 * @author Balovtsev
 * @since 12.05.2014
 */
@XmlType(propOrder = {"id", "name"})
public class Category
{
	private Long   id;
	private String name;

	/**
	 */
	public Category() {}

	/**
	 *
	 * @param id идентификатор
	 * @param name название
	 */
	public Category(Long id, String name)
	{
		this.id = id;
		this.name = name;
	}

	/**
	 * Обязательный элемент
	 * @return Идентификатор
	 */
	@XmlElement(name = "id", required = true)
	public Long getId()
	{
		return id;
	}

	/**
	 * Обязательный элемент
	 * @return Название
	 */
	@XmlJavaTypeAdapter(CDATAXmlAdapter.class)
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
