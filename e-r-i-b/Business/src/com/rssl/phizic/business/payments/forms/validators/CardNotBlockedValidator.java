package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.business.resources.external.NotBlockedCardFilter;

import java.util.Map;

/**
 * Валидатор, отсеивающий заблокированные карты
 * @author Pankin
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class CardNotBlockedValidator extends AccountAndCardValidatorBase
{
	private static final String EXTERNAL_CARD_NUMBER = "externalCardNumber";

	public boolean validate(Map values) throws TemporalDocumentException		
	{
		Card externalCard = getExternalCard(EXTERNAL_CARD_NUMBER, values);
		return new NotBlockedCardFilter().accept(externalCard);
	}
}
