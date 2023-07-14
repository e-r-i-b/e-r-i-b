package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Ёлемент дл€ справочных данных вида ключ:значение
 * @author Pankin
 * @ created 17.06.14
 * @ $Author$
 * @ $Revision$
 */
@XmlType(propOrder = {"key", "value"})
@XmlRootElement(name = "property")
public class Property
{
	private String key;
	private String value;

	public Property()
	{
	}

	public Property(String key, String value)
	{
		this.key = key;
		this.value = value;
	}

	@XmlElement(name = "key", required = true)
	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	@XmlElement(name = "value", required = true)
	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
