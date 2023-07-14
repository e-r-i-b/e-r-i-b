package com.rssl.phizic.gate.templates.attributable;

import com.rssl.phizic.gate.documents.attribute.Type;
import com.rssl.phizic.utils.StringHelper;

/**
 * Доп. атрибут типа Long
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class LongAttribute extends ExtendedAttributeBase
{
	protected LongAttribute() {};

	protected LongAttribute(String name, Object value)
	{
		super(name, value);
	}

	protected LongAttribute(Long id, String name, Object value)
	{
		super(id, name, value);
	}

	public Type getType()
	{
		return Type.LONG;
	}

	public Long getValue()
	{
		String value = getStringValue();
		if (StringHelper.isEmpty(value))
		{
			return null;
		}

		return Long.valueOf(Long.parseLong(value));
	}

	protected String format(Object value)
	{
		if (value == null || value instanceof String)
		{
			return super.format(value);
		}

		assert value.getClass().equals(Long.class);
		return value.toString();
	}
}
