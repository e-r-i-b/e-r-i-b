package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.DateHelper;

import java.util.Date;
import java.util.Map;

/**
 * @author Erkin
 * @ created 29.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class DatePeriodMultiFieldValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_DATE_FROM = "dateFrom";
	public static final String FIELD_DATE_TO = "dateTo";
	public static final String TYPE_PERIOD_WEEK = "week";
	public static final String TYPE_PERIOD_MONTH = "month";
	public static final String FIELD_PERIOD_TYPE = "periodType";

	private static final int ONE_WEEK_MILLISS = 6 * 24 * 3600 * 1000;
	private static final Long ONE_MONTH_MILLISS_MIN = 1000L*3600*24*27; //минимальное значение для периода "month"
	private static final Long ONE_MONTH_MILLISS_MAX = 1000L*3600*24*30; //максимальное значение для периода "month"

	/**
	 * Максимально допустимое значение периода в миллисекундах
	 */
	private Long maxTimeSpan = null;

	///////////////////////////////////////////////////////////////////////////

	public Long getMaxTimeSpan()
	{
		return maxTimeSpan;
	}

	public void setMaxTimeSpan(Long maxTimeSpan)
	{
		this.maxTimeSpan = maxTimeSpan;
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Date from = (Date) retrieveFieldValue(FIELD_DATE_FROM, values);
		Date to = (Date) retrieveFieldValue(FIELD_DATE_TO, values);
		//Если не указали одну из дат то не проверяем.
		if (from == null || to == null)
			return true;
		String periodType = null;
		if  (values.containsKey(FIELD_PERIOD_TYPE))
			periodType = (String) retrieveFieldValue(FIELD_PERIOD_TYPE, values);

		if (maxTimeSpan != null)
			return Math.abs(DateHelper.diff(to, from)) <= maxTimeSpan;
		else if (periodType != null && TYPE_PERIOD_WEEK.equals(periodType))
		{
			return DateHelper.diff(to, from) == ONE_WEEK_MILLISS;
		}
		else if (periodType != null && TYPE_PERIOD_MONTH.equals(periodType))
		{
			Long period = DateHelper.diff(to, from);
			return (period >= ONE_MONTH_MILLISS_MIN &&  period <= ONE_MONTH_MILLISS_MAX);
		}
		return true;
	}
}
