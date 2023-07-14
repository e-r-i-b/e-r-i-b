package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.PropertyConfig;

import java.util.Map;

/**
 * @author vagin
 * @ created 09.09.14
 * @ $Author$
 * @ $Revision$
 * Валидатор проверки - разрешены ли создания конверсионных копилок.
 */
public class MoneyBoxConversionValidator extends MultiFieldsValidatorBase
{
	private static final String FIELD_TO_RESOURCE = "toResource";
	private static final String FIELD_FROM_RESOURCE = "fromResource";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Object fromResource = retrieveFieldValue(FIELD_FROM_RESOURCE, values);
		Object toResource = retrieveFieldValue(FIELD_TO_RESOURCE, values);
		if (fromResource instanceof CardLink && toResource instanceof AccountLink)
		{
			String fromResourceCurrencyCode = ((CardLink) fromResource).getCurrency().getCode();
			String toResourceCurrencyCode = ((AccountLink) toResource).getCurrency().getCode();
			if (CurrencyUtils.isSameCurrency(fromResourceCurrencyCode, toResourceCurrencyCode))
			{
				return true;
			}
			else
			{
				PropertyConfig config = ConfigFactory.getConfig(PropertyConfig.class);
				return config.getProperty("com.rssl.iccs.moneybox.disallowedConversion.enable").equals("false");
			}
		}
		return true;
	}
}
