package com.rssl.phizic.business.creditcards.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Валидатор карточного кредитного продукта
 * Если включен льготный период, то д.б. заданы период и ставка
 * @author Dorzhinov
 * @ created 20.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class GracePeriodValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_ALLOW_GRACE_PERIOD = "allowGracePeriod";
	public static final String FIELD_GRACE_PERIOD_DURATION = "gracePeriodDuration";
	public static final String FIELD_GRACE_PERIOD_INTEREST_RATE = "gracePeriodInterestRate";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Boolean allowGracePeriod = (Boolean) retrieveFieldValue(FIELD_ALLOW_GRACE_PERIOD, values);
		Integer gracePeriodDuration = (Integer) retrieveFieldValue(FIELD_GRACE_PERIOD_DURATION, values);    
		BigDecimal gracePeriodInterestRate = (BigDecimal) retrieveFieldValue(FIELD_GRACE_PERIOD_INTEREST_RATE, values);

		return !(allowGracePeriod && (gracePeriodDuration == null || gracePeriodInterestRate == null));
	}
}
