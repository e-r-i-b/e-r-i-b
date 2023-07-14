package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ikfl
 * Date: 08.09.2010
 * Time: 11:44:41
 * To change this template use File | Settings | File Templates.
 */
public class PaymentCurrencyPermittedValidator extends MultiFieldsValidatorBase
{
	private static final String ALLOWED_CURRENCIES = "currencies";    //коды разрешенных валют, через DELIMITER
	private static final String DELIMITER = ";";
	private static final String FIELD_CURRENCY = "amountCurrency";

	private String currencies;

	public PaymentCurrencyPermittedValidator()
	{
		this("Платеж в данной валюте совершить нельзя.");
	}

	/**
	 * ctor
	 * @param message - сообщение
	 */
	public PaymentCurrencyPermittedValidator(String message)
	{
		super.setMessage(message);
	}


	public boolean validate(Map values) throws TemporalDocumentException
	{
		currencies = getParameter(ALLOWED_CURRENCIES);
		String currency = (String) retrieveFieldValue(FIELD_CURRENCY, values);
		if (currency == null)
	    {
		    return true;
	    }

		for (String curr : currencies.split(DELIMITER))
		{
			if (curr.equals(currency))
				return true;
		}
		return false;
	}
}
