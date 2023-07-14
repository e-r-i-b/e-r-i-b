package com.rssl.phizic.gate.templates.attributable;

import com.rssl.phizic.gate.documents.attribute.Type;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static com.rssl.phizic.logging.Constants.LOG_MODULE_CORE;

/**
 * Доп. атрибут типа Date
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class DateAttribute extends ExtendedAttributeBase
{
	private static final String DEBUG_MESSAGE_BUG86331 = "Ошибка в получении значения доп. поля: name = %s, type = %s";
	private static final Log LOG = PhizICLogFactory.getLog(LOG_MODULE_CORE);

	protected DateAttribute() {};

	protected DateAttribute(String name, Object value)
	{
		super(name, value);
	}

	protected DateAttribute(Long id, String name, Object value)
	{
		super(id, name, value);
	}

	public Type getType()
	{
		return Type.DATE;
	}

	public Date getValue()
	{
		String value = getStringValue();
		if (StringHelper.isEmpty(value))
		{
			return null;
		}

		try
		{
			return DateHelper.fromXMlDateToDate(value);
		}
		catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
	}

	protected String format(Object value)
	{
		if (value == null || value instanceof String)
		{
			return super.format(value);
		}

		if (value instanceof Calendar)
		{
			LOG.error(String.format("ЕСУШ: " + DEBUG_MESSAGE_BUG86331, getName(), getType()));

			Calendar calendar = (Calendar) value;
			return DateHelper.toXMLDateFormat(calendar.getTime());
		}

		return DateHelper.toXMLDateFormat((Date) value);
	}
}
