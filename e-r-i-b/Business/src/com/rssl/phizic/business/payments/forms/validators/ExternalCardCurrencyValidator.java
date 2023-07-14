package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author krenev
 * @ created 27.04.2010
 * @ $Author$
 * @ $Revision$
 * Валидатор соотвествия "введенной" валюты и валюты внешней(не клиента) карточки
 */
public class ExternalCardCurrencyValidator extends AccountAndCardValidatorBase
{
	public static final String FIELD_CURRENCY = "currency";
	public static final String FIELD_CARD_NUMBER = "cardNumber";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String currencyCode = (String) retrieveFieldValue(FIELD_CURRENCY, values);
		String cardNumber = (String) retrieveFieldValue(FIELD_CARD_NUMBER, values);
		if (StringHelper.isEmpty(currencyCode) && StringHelper.isEmpty(cardNumber))
		{
			return true;
		}
		Currency cardCurrency = getExternalCard(cardNumber, values).getCurrency();
		return cardCurrency.getCode().equals(currencyCode);
	}
}

