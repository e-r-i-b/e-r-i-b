package com.rssl.phizic.utils.jaxb;

import java.text.SimpleDateFormat;

/**
 * @author Erkin
 * @ created 13.02.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * JAXB-Конвертация java:Calendar в/из xsd:dateTime
 */
public class CalendarDateTimeAdapter extends AbstractCalendarAdapter
{
	@Override
	protected SimpleDateFormat getDateFormat()
	{
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	}
}
