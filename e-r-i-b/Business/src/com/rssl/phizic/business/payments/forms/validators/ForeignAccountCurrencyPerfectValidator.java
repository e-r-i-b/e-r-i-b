package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.Currency;

import java.util.Map;

/**
 * @author Kosyakova
 * @ created 21.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class ForeignAccountCurrencyPerfectValidator extends AccountAndCardValidatorBase
{
    public static final String FIELD_PAYER_ACCOUNT = "payerAccount";
    public static final String FIELD_RECEIVER_ACCOUNT   = "receiverAccount";
	public static final String FIELD_FOREIGN_CURRENCY = "foreignCurrency";
	public static final String FIELD_TYPE   = "type"; // = SALE, если клиент продает, BUY - если покупает

    public boolean validate(Map values) throws TemporalDocumentException
    {
        Currency accountCurrency = null;
	    String purchase = (String) retrieveFieldValue(FIELD_TYPE, values);
	    String accountNumber = "BUY".equals(purchase) ? FIELD_RECEIVER_ACCOUNT : FIELD_PAYER_ACCOUNT;
        String foreignCurrency = (String) retrieveFieldValue(FIELD_FOREIGN_CURRENCY, values);

        accountCurrency = getAccountCurrency(accountNumber, values);
	    if (accountCurrency == null)
	        return true;

        return accountCurrency.getCode().equals(foreignCurrency);
    }
}
