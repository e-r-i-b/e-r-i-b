package com.rssl.phizic.config.loanreport;

import com.rssl.phizic.common.types.annotation.NonThreadSafe;
import com.rssl.phizic.common.types.annotation.Statefull;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 19.11.2014
 * @ $Author$
 * @ $Revision$
 */
@Statefull
@NonThreadSafe
class PropertyWriter
{
	private final Map<String, String> properties = new HashMap<String, String>();

	void writeOptionalCalendar(String propertyName, Calendar propertyValue)
	{
		if (StringHelper.isEmpty(propertyName))
			throw new IllegalArgumentException("Не указано имя настройки");

		if (propertyValue != null)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(CreditBureauConstants.DATE_TIME_FORMAT);
			properties.put(propertyName, dateFormat.format(DateHelper.toDate(propertyValue)));
		}
		else properties.put(propertyName, null);
	}

	void flush()
	{
		if (!properties.isEmpty())
		{
			DbPropertyService.updateProperties(properties, CreditBureauConstants.PROPERTY_CATEGORY, null);
			properties.clear();
		}
	}
}
