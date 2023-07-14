package com.rssl.phizic.business.documents.templates.attributes;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;

/**
 * Базовый класс доп. атрибутов
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class ExtendedAttributeBase implements ExtendedAttribute
{
	private Long id;
	private String name;
	private String stringValue;
	private boolean changed;

	protected ExtendedAttributeBase() {}

	protected ExtendedAttributeBase(String name, Object value)
	{
		this.name = name;
		this.stringValue = format(value);
	}

	protected ExtendedAttributeBase(Long id, String name, Object value)
	{
		this(name, value);
		this.id = id;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setValue(Object value)
	{
		this.stringValue = format(value);
	}

	public String getStringValue()
	{
		return stringValue;
	}

	public void setStringValue(String stringValue)
	{
		this.stringValue = stringValue;
	}

	protected String format(Object value)
	{
		if (StringHelper.isEmpty((String) value))
		{
			return StringUtils.EMPTY;
		}
		return (String) value;
	}

	public boolean isChanged()
	{
		return changed;
	}

	public void setChanged(boolean changed)
	{
		this.changed = changed;
	}
}
