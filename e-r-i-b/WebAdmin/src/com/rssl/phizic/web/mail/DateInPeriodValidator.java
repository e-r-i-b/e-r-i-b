package com.rssl.phizic.web.mail;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.config.ConfigFactory;

import java.util.Date;
import java.util.Map;

/**
 * @author muhin
 * @ created 18.05.15
 * @ $Author$
 * @ $Revision$
 */
public class DateInPeriodValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_FROM_DATE = "from_date";
	public static final String FIELD_TO_DATE =   "to_date";
	public static final String PERIOD =   "period";

	/**
	 * Проверяет на то, что даты отличаются друг от друга не более чем на период, передаваемы в параметре
	 * @param values значения для проверки. Key - имя поля (в форме), Value - значение поля.
	 */
	public boolean validate(Map values) throws TemporalDocumentException
	{
		Date fromDate = (Date) ((Date)retrieveFieldValue(FIELD_FROM_DATE, values)).clone();
		Date toDate   = (Date) ((Date)retrieveFieldValue(FIELD_TO_DATE,   values)).clone();
		Long period = ConfigFactory.getConfig(MailConfig.class).getDefaultPeriod();
		if (fromDate == null || toDate == null || period == null)
			return true;
		fromDate.setDate(fromDate.getDate() + period.intValue());

		int resultDateCompare = normalize(fromDate.compareTo(toDate));
		this.setMessage("Пожалуйста, укажите период, не превышающий " + period + " дней.");
		return ((resultDateCompare > 0) || (resultDateCompare == 0));
	}

	private static int normalize(int compareResult)
	{
		if(compareResult == 0)
			return 0;
		return compareResult > 0 ? 1 : -1;
	}
}
