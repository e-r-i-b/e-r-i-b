package com.rssl.phizic.gate.templates.attributable;

import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;
import com.rssl.phizic.gate.documents.attribute.Type;

/**
 * Приемник данных из веб-сервиса
 *
 * @author khudyakov
 * @ created 07.05.14
 * @ $Author$
 * @ $Revision$
 */
public class CommonExtendedAttribute implements ExtendedAttribute
{
	private String name;
	private Type type;
	private Object value;
	private String stringValue;
	private boolean changed;


	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	public void setType(String type)
	{
		this.type = Type.valueOf(type);
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public String getStringValue()
	{
		return stringValue;
	}

	public void setStringValue(String stringValue)
	{
		this.stringValue = stringValue;
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
