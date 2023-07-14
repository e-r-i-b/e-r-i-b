package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.DateHelper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Валидатор даты досрочного погашения<br/>
 * Проверяет, раньше ли дата досрочного погашения чем дата окончания кредита<br/>
 * Created with IntelliJ IDEA.
 * User: petuhov
 * Date: 19.06.15
 * Time: 14:07 
 */
public class EarlyLoanRepaymentClosureDateValidator extends MultiFieldsValidatorBase
{
	public boolean validate(Map values) throws TemporalDocumentException
	{
		try
		{
			Long loanLinkId = (Long) values.get("loanLinkId");
			return PersonContext.getPersonDataProvider().getPersonData().getLoan(loanLinkId).getLoan().getTermEnd().getTime().after((Date) values.get("documentDate"));
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
