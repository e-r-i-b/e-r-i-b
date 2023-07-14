package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.calendar.CalendarService;
import com.rssl.phizic.business.calendar.WorkDay;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.Query;
import org.hibernate.Session;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Проверка на то что указаннная дата в ТБ клиента является рабочим днем<br/>
 * Created with IntelliJ IDEA.
 * User: petuhov
 * Date: 18.06.15
 * Time: 17:04 
 */
public class EarlyLoanRepWorkDayValidator extends MultiFieldsValidatorBase
{
	private static final CalendarService calendarService = new CalendarService();

	private boolean isWorkDay(final Calendar date,final String tb) throws TemporalDocumentException
	{
		try
		{
			return calendarService.getIsWorkDayByTB(date,tb);
		}
		catch (Exception e)
		{
			throw new TemporalDocumentException(e);
		}
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		try
		{
			Long loanLinkId = (Long) values.get("loanLinkId");
			Loan loan = PersonContext.getPersonDataProvider().getPersonData().getLoan(loanLinkId).getLoan();
			return isWorkDay(DateHelper.toCalendar((Date) values.get("documentDate")),loan.getOffice().getCode().getFields().get("region"));
		}
		catch(BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new TemporalDocumentException(e);
		}
	}
}
