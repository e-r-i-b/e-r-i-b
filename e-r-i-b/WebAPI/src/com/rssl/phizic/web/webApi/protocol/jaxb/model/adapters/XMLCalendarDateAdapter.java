package com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Адаптер для преобразования объектов типа Calendar в дату вида dd.MM.yyyy
 *
 * @author Jatsky
 * @ created 21.07.14
 * @ $Author$
 * @ $Revision$
 */

public class XMLCalendarDateAdapter extends XmlCalendarAdapter
{
	@Override
	public String marshal(Calendar value) throws Exception
	{
		DateFormat format = new SimpleDateFormat(DATE_FORMAT);
		return format.format(value.getTime());
	}
}
