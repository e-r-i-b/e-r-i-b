package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * Валидатор проверяет необходимость ввода места получения предодобренной карты
 *
 * @ author: Gololobov
 * @ created: 01.09.14
 * @ $Author$
 * @ $Revision$
 */
public class PreapprovedLoanCardClaimCreditCardOfficeFieldValidator extends RequiredFieldValidator
{
	public boolean validate(String value) throws TemporalDocumentException
	{
		try
		{
			if (isValueEmpty(value))
				return !PersonHelper.isCreditCardOfficeExist();
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
		return true;

	}
}
