package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.resources.external.*;

import java.util.Map;

/**
 * @author: vagin
 * @ created: 12.05.2012
 * @ $Author$
 * @ $Revision$
 * Валидатор проверки подходит ли ресурс списания по фильтрам(для биллинговых платежей и шинных автоплатежей)
 */
public class FromResourceFilterValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_FROM_RESOURCE = "fromResource";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		EditableExternalResourceLink fromResource = (EditableExternalResourceLink)retrieveFieldValue(FIELD_FROM_RESOURCE, values);

		if(fromResource instanceof CardLink)
			return new ActiveNotVirtualCardsFilter().accept(((CardLink)fromResource).getCard());
		if(fromResource instanceof AccountLink)
			return new ActiveDebitRurAccountFilter().accept((AccountLink)fromResource);
		return false;
	}
}

