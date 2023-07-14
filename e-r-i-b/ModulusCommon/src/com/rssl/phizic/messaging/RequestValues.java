package com.rssl.phizic.messaging;

import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

import java.util.Calendar;
import java.util.Map;

/**
 * @author Gulov
 * @ created 02.07.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Значения СМС-запроса, для записи в журнал сообщений.
 * Если значения, полученные из СМС-запроса не определены, то возвращаются значения заглушки.
 */
class RequestValues
{
	static final String UID_TAG_NAME = "rqUID";
	static final String TIME_TAG_NAME = "rqTime";
	private static final String FAKE_ID = "fake-id";

	private final Map<String, String> values;

	RequestValues(Map<String, String> values)
	{
		this.values = values;
	}

	String getUID()
	{
		String result = values.get(UID_TAG_NAME);
		return result == null ? FAKE_ID : result;
	}

	long getTime()
	{
		String result = values.get(TIME_TAG_NAME);
		return result == null ? Calendar.getInstance().getTimeInMillis() : XMLDatatypeHelper.parseDate(result).getTimeInMillis();
	}
}
