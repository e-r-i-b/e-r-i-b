package com.rssl.phizic.business.documents.templates.attributes;

import com.rssl.phizic.gate.documents.attribute.Type;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Доп. атрибут типа DateTime
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class DateTimeAttribute extends ExtendedAttributeBase
{
	protected DateTimeAttribute() {};

	protected DateTimeAttribute(String name, Object value)
	{
		super(name, value);
	}

	protected DateTimeAttribute(Long id, String name, Object value)
	{
		super(id, name, value);
	}

	public Type getType()
	{
		return Type.DATE_TIME;
	}

	public GregorianCalendar getValue()
	{
		String value = getStringValue();
		if (StringHelper.isEmpty(value))
		{
			return null;
		}

		return XMLDatatypeHelper.parseDateTime(value);
	}

	protected String format(Object value)
	{
		if (value == null || value instanceof String)
		{
			return super.format(value);
		}

		return XMLDatatypeHelper.formatDateTime((Calendar) value);
	}
}
