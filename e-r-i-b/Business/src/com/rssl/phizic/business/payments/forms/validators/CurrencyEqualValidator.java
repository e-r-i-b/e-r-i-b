package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.CurrencyUtils;

import java.util.Map;

/**
 * @author Erkin
 * @ created 29.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyEqualValidator extends AccountAndCardValidatorBase
{
	private static final String FIELD_FROM_CURRENCY = "fromCurrency";

	private static final String FIELD_TO_CURRENCY = "toCurrency";

	///////////////////////////////////////////////////////////////////////////

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String fromCurrencyCode = (String) retrieveFieldValue(FIELD_FROM_CURRENCY, values);
		if (StringHelper.isEmpty(fromCurrencyCode))
			return false;

		String toCurrencyCode = (String) retrieveFieldValue(FIELD_TO_CURRENCY, values);
		if (StringHelper.isEmpty(toCurrencyCode))
			return false;

		return CurrencyUtils.isSameCurrency(fromCurrencyCode, toCurrencyCode);
	}
}
