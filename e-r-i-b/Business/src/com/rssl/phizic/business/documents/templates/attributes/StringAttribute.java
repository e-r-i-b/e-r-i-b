package com.rssl.phizic.business.documents.templates.attributes;

import com.rssl.phizic.gate.documents.attribute.Type;

/**
 * Доп. атрибут типа String
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class StringAttribute extends ExtendedAttributeBase
{
	protected StringAttribute() {};

	protected StringAttribute(String name, Object value)
	{
		super(name, value);
	}

	protected StringAttribute(Long id, String name, Object value)
	{
		super(id, name, value);
	}

	public Type getType()
	{
		return Type.STRING;
	}

	public String getValue()
	{
		return getStringValue();
	}
}
