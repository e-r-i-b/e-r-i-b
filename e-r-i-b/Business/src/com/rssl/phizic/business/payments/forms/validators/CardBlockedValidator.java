package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.BlockedCardFilter;
import com.rssl.phizic.business.resources.external.CardLink;

import java.util.Map;

/**
 * Валидатор заблокированности карты.
 *
 * @author bogdanov
 * @ created 25.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class CardBlockedValidator extends MultiFieldsValidatorBase
{
	private static final String CARD_NAME = "card";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		CardLink cardLink = (CardLink) retrieveFieldValue(CARD_NAME, values);
		return new BlockedCardFilter().evaluate(cardLink);
	}
}
