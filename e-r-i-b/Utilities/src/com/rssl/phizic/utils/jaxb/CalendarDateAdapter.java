package com.rssl.phizic.utils.jaxb;

import java.text.SimpleDateFormat;

/**
 * @author Erkin
 * @ created 13.02.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * JAXB-Конвертация java:Calendar в/из xsd:date
 */
public class CalendarDateAdapter extends AbstractCalendarAdapter
{
	@Override
	protected SimpleDateFormat getDateFormat()
	{
		return new SimpleDateFormat("yyyy-MM-dd");
	}
}