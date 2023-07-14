package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.earlyloanrepayment.EarlyLoanRepaymentConfig;
import com.rssl.phizic.utils.DateHelper;

import java.text.ParseException;
import java.util.Calendar;

/**
 * ¬алидатор проверки даты досрочного погашени€ кредита<br/>
 * ѕровер€ет, допустима ли дата досрочного погашени€ по настройкам досрочного погашени€<br/>
 * Created with IntelliJ IDEA.
 * User: petuhov
 * Date: 18.06.15
 * Time: 16:31 
 */
public class EarlyLoanRepaymentDateValidator extends FieldValidatorBase
{
	public boolean validate(String value) throws TemporalDocumentException
	{
		try
		{
			Calendar date = DateHelper.parseCalendar(value);
			Calendar minDate = DateHelper.getCurrentDate();
			minDate.add(Calendar.DAY_OF_YEAR, ConfigFactory.getConfig(EarlyLoanRepaymentConfig.class).getMinDate());
			if(date.before(minDate))
				return false;
			int maxDateSetting = ConfigFactory.getConfig(EarlyLoanRepaymentConfig.class).getMaxDate();
			if(maxDateSetting>0)
			{
				Calendar maxDate = DateHelper.getCurrentDate();
				maxDate.add(Calendar.DAY_OF_YEAR, maxDateSetting);
				if(date.after(maxDate))
					return false;
			}
			return true;
		}
		catch (ParseException ignore)
		{
			return false;
		}
	}
}
