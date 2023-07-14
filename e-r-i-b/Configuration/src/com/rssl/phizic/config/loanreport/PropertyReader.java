package com.rssl.phizic.config.loanreport;

import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.config.*;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PropertyHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.BooleanUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
* @author Erkin
* @ created 19.11.2014
* @ $Author$
* @ $Revision$
*/

/**
 * Ридер настроек из БД либо iccs.properties
 */
class PropertyReader
{
	private final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final Properties properties;

	/**
	 * ctor
	 */
	PropertyReader()
	{
		Properties defaultProperties = PropertyHelper.readProperties("iccs.properties");

		properties = new Properties(defaultProperties);

		List<Property> list = DbPropertyService.findProperties(CreditBureauConstants.PROPERTY_CATEGORY.getValue(), null);
		for (Property property : list)
			properties.setProperty(property.getKey(), property.getValue());

	}

	String readOptionalString(String propertyName)
	{
		String propertyValue = properties.getProperty(propertyName);
		return StringHelper.getNullIfEmpty(propertyValue);
	}

	String readMandatoryString(String propertyName)
	{
		String propertyValue = properties.getProperty(propertyName);
		if (StringHelper.isEmpty(propertyValue))
			throw new ConfigurationException("Отсутствует настройка " + propertyName);
		return propertyValue;
	}

	boolean readMandatoryBoolean(String propertyName)
	{
		String booleanString = readMandatoryString(propertyName);
		return BooleanUtils.toBoolean(booleanString);
	}

	int readMandatoryInteger(String propertyName)
	{
		String integerString = readMandatoryString(propertyName);
		return Integer.parseInt(integerString);
	}

	Calendar readOptionalCalendar(String propertyName)
	{
		String dateString = readOptionalString(propertyName);
		if (StringHelper.isEmpty(dateString))
			return null;

		SimpleDateFormat dateFormat = new SimpleDateFormat(CreditBureauConstants.DATE_TIME_FORMAT);
		try
		{
			Date date = dateFormat.parse(dateString);
			return DateHelper.toCalendar(date);
		}
		catch (ParseException e)
		{
			log.error("Настройка " + propertyName + " содержит непонятную дату: " + dateString, e);
			return null;
		}
	}

	UUID readMandatoryUUID(String propertyName)
	{
		String uuidString = readMandatoryString(propertyName);
		return UUID.fromValue(uuidString);
	}

	URL readMandatoryURL(String propertyName)
	{
		String urlString = readMandatoryString(propertyName);
		try
		{
			return new URL(urlString);
		}
		catch (MalformedURLException e)
		{
			throw new IllegalArgumentException("Настройка " + propertyName + " содержит непонятный URL: " + urlString, e);
		}
	}

	DayOfMonth readMandatoryDayOfMonth(String propertyName)
	{
		String dayString = readMandatoryString(propertyName);
		return DayOfMonth.fromString(dayString);
	}

	TimeOfDay readMandatoryTimeOfDay(String propertyName)
	{
		String timeString = readMandatoryString(propertyName);
		return TimeOfDay.fromHHMMSS(timeString);
	}
}
