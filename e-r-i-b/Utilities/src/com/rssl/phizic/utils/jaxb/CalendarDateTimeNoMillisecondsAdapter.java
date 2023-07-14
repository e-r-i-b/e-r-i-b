package com.rssl.phizic.utils.jaxb;

import java.text.SimpleDateFormat;

/**
 * @author Rtischeva
 * @ created 02.06.15
 * @ $Author$
 * @ $Revision$
 */
public class CalendarDateTimeNoMillisecondsAdapter extends AbstractCalendarAdapter
{
	@Override
	protected SimpleDateFormat getDateFormat()
	{
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	}
}
