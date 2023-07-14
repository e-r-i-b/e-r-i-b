package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PropertyConfig;
import org.apache.commons.lang.BooleanUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.zip.ZipEntry;

/**
 * @author khudyakov
 * @ created 16.11.14
 * @ $Author$
 * @ $Revision$
 * Валидатор длительности даты ближайшего платежа, т.е. нельзя задать дату слишком удаленную от текущей даты. Ограничения по длительности зависят от периодичности автоплатежа:
	 o	Раз в неделю - текущая дата + 1 месяц (если копилка подключается 20.08, то доступны все даты до 20.09 включительно, если подключать 20.09, то доступны все даты до 20.10 включительно).
	 o	Раз в месяц - текущая дата + 1 месяц (если копилка подключается 20.08, то доступны все даты до 20.09 включительно, если подключать 20.09, то доступны все даты до 20.10 включительно).
	 o	Раз в квартал - текущая дата + 3 месяца (1 квартал) (если копилка подключается 20.08, то доступны все даты до 20.11 включительно)
	 o	Раз в год - текущая дата + 1 год (если копилка подключается 20.08.2014, то доступны все даты до 20.08.2015 включительно).
 */
public class StartDateDistanceValidator extends MultiFieldsValidatorBase
{
	private static final String MESSAGE                     = "Дата ближайшего платежа должна быть не позднее %s.";
	private static final String PROPERTY_PARAMETER_NAME     = "com.rssl.phizia.autopayments.isNearestDateRestrictionActive";
	private static final String START_DATE_FIELD_NAME       = "startDate";
	private static final String EVENT_TYPE_FIELD_NAME       = "eventType";

	private ThreadLocal<String> message = new ThreadLocal<String>();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		if (!isCheckEnabled())
		{
			return true;
		}

		Date startDate = (Date) retrieveFieldValue(START_DATE_FIELD_NAME, values);
		Calendar actualDay = getActualDay(ExecutionEventType.valueOf((String) retrieveFieldValue(EVENT_TYPE_FIELD_NAME, values)));

		boolean result = actualDay.getTime().compareTo(startDate) >= 0;
		if (!result)
		{
			setMessage(getMessage(actualDay));
		}
		return result;
	}

	private boolean isCheckEnabled()
	{
		PropertyConfig config = ConfigFactory.getConfig(PropertyConfig.class);
		return BooleanUtils.toBoolean(config.getProperty(PROPERTY_PARAMETER_NAME));
	}

	private Calendar getActualDay(ExecutionEventType eventType) throws TemporalDocumentException
	{
		Calendar currentDay = Calendar.getInstance();
		switch (eventType)
		{
			case ONCE_IN_WEEK:
			case ONCE_IN_MONTH:
				currentDay.add(Calendar.MONTH, 1);
				break;
			case ONCE_IN_QUARTER:
				currentDay.add(Calendar.MONTH, 3);
				break;
			case ONCE_IN_YEAR:
				currentDay.add(Calendar.YEAR, 1);
				break;
			default:
				throw new TemporalDocumentException("Некорреткный тип периодичности.");
		}
		return currentDay;
	}

	private String getMessage(Calendar calendar)
	{
		return String.format(MESSAGE, new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTime()));
	}

	@Override
	public void setMessage(String msg)
	{
		message.set(msg);
	}

	@Override
	public String getMessage()
	{
		return message.get();
	}
}
