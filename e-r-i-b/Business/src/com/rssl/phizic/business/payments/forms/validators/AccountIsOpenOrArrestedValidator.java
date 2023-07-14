package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ActiveOrArrestedAccountFilter;

/**
 * Валидатор для проверки что аккаунт открыт или арестован
 * @author basharin
 * @ created 11.09.14
 * @ $Author$
 * @ $Revision$
 */

public class AccountIsOpenOrArrestedValidator extends AccountIsOpenValidator
{

	protected boolean accept(AccountLink accountLink) throws TemporalBusinessException
	{
		return (new ActiveOrArrestedAccountFilter()).accept(accountLink.getAccount());
	}
}
