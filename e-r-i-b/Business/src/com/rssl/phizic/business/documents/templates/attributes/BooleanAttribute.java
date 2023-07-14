package com.rssl.phizic.business.documents.templates.attributes;

import com.rssl.phizic.gate.documents.attribute.Type;
import org.apache.commons.lang.BooleanUtils;

/**
 * Доп. атрибут типа Boolean
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class BooleanAttribute extends ExtendedAttributeBase
{
	protected BooleanAttribute() {};

	protected BooleanAttribute(String name, Object value)
	{
		super(name, value);
	}

	protected BooleanAttribute(Long id, String name, Object value)
	{
		super(id, name, value);
	}

	public Type getType()
	{
		return Type.BOOLEAN;
	}

	public Boolean getValue()
	{
		return Boolean.valueOf(getStringValue());
	}

	protected String format(Object value)
	{
		if (value == null || value instanceof String)
		{
			return super.format(value);
		}

		return Boolean.valueOf(BooleanUtils.toBoolean((Boolean) value)).toString();
	}
}
