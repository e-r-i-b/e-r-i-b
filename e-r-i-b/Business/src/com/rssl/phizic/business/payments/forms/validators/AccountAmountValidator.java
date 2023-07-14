package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.Account;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Проверка что на сумма на счете не меньше чем проверяемая
 * Проверка работает с допущением что сумма на счете и проверяемая сумма в одной валюте
 * @author Evgrafov
 * @ created 05.12.2005
 * @ $Author: pankin $
 * @ $Revision: 46205 $
 */

public class AccountAmountValidator extends AmountValidatorBase
{
	public static final String FIELD_ACCOUNT = "account";

	protected BigDecimal getCheckedAmount(Map values) throws TemporalDocumentException
	{
		Account account = (Account) retrieveFieldValue(FIELD_ACCOUNT, values);
		if (account == null)
			return null;

		try
		{
			PersonData data = PersonContext.getPersonDataProvider().getPersonData();
			return getAccountAmount(data.findAccount(account.getNumber()));
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new RuntimeException(e);
		}
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Account account = (Account) retrieveFieldValue(FIELD_ACCOUNT, values);
		if (account instanceof AbstractStoredResource)
			return true;

		return super.validate(values);
	}
}
