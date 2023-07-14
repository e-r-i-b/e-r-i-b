package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.Currency;

import java.util.Map;

/**
 * @author Kosyakova
 * @ created 22.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class AccountCurrencyEqualValidator extends AccountAndCardValidatorBase
{
	public static final String FIELD_ACCOUNT = "account";
	public static final String FIELD_CURRENCY   = "currency";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Currency accountCurrency = null;
		
		accountCurrency = getAccountCurrency(FIELD_ACCOUNT, values);
		if (accountCurrency == null)
			return true;

	    String currencyCode = (String) retrieveFieldValue(FIELD_CURRENCY, values);

		return accountCurrency.getCode().equals(currencyCode);
	}
}

