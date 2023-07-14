package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * Валидатор отсекает операции перевода между своми счетами и картами при покупке/продаже металлов
 *
 * @ author: Gololobov
 * @ created: 28.11.14
 * @ $Author$
 * @ $Revision$
 */
public class IMAPaymentFromAndToResourceValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_FROM_RESOURCE_TYPE = "fromResourceType";
	public static final String FIELD_TO_RESOURCE_TYPE = "toResourceType";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String fromResourceType = (String) retrieveFieldValue(FIELD_FROM_RESOURCE_TYPE, values);
		String toResourceType = (String) retrieveFieldValue(FIELD_TO_RESOURCE_TYPE, values);
		if (StringHelper.isNotEmpty(fromResourceType) && StringHelper.isNotEmpty(toResourceType) &&
				!fromResourceType.equals(IMAccountLink.class.getName()) && !toResourceType.equals(IMAccountLink.class.getName()))
			return false;

		return true;
	}
}
