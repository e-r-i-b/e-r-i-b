package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.Currency;

import java.util.Map;

/**
 * Проверка на то, что валюта счета списания и валюта счета зачисления являются разными.
 * @author Kidyaev
 * @ created 05.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class AccountCurrenciesNotEqualValidator extends AccountAndCardValidatorBase
{
    public static final String FIELD_FROM_ACCOUNT = "fromAccount";
    public static final String FIELD_TO_ACCOUNT   = "toAccount";

    public boolean validate(Map values) throws TemporalDocumentException
    {
        Currency fromAccountCurrency = null;
        Currency toAccountCurrency   = null;

        fromAccountCurrency = getAccountCurrency(FIELD_FROM_ACCOUNT, values);
        toAccountCurrency = getAccountCurrency(FIELD_TO_ACCOUNT, values);
        if (fromAccountCurrency == null || toAccountCurrency == null)
	        return true;    

        return !fromAccountCurrency.getCode().equals(toAccountCurrency.getCode());
    }
}
