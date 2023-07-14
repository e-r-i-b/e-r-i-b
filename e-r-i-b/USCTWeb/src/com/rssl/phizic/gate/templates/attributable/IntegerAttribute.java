package com.rssl.phizic.gate.templates.attributable;

import com.rssl.phizic.gate.documents.attribute.Type;
import com.rssl.phizic.utils.StringHelper;

/**
 * Доп атрибут типа Integer
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class IntegerAttribute extends ExtendedAttributeBase
{
	protected IntegerAttribute() {};

	protected IntegerAttribute(String name, Object value)
	{
		super(name, value);
	}

	protected IntegerAttribute(Long id, String name, Object value)
	{
		super(id, name, value);
	}

	public Type getType()
	{
		return Type.INTEGER;
	}

	public Integer getValue()
	{
		String value = getStringValue();
		if (StringHelper.isEmpty(value))
		{
			return null;
		}

		return Integer.valueOf(Integer.parseInt(value));
	}

	protected String format(Object value)
	{
		if (value == null || value instanceof String)
		{
			return super.format(value);
		}

		assert value.getClass().equals(Integer.class);
		return value.toString();
	}
}
