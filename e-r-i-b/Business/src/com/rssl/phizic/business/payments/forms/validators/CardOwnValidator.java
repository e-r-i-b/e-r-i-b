package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.resources.external.CardOwnFIOFilter;
import com.rssl.phizic.gate.bankroll.Card;

import java.util.Map;

/**
 * Валидатор сравнивает полное имя держателя карты и текущего клиента
 * @author khudyakov
 * @ created 03.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class CardOwnValidator extends AccountAndCardValidatorBase
{
	private static final String EXTERNAL_CARD_NUMBER = "externalCardNumber";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Card externalCard = getExternalCard(EXTERNAL_CARD_NUMBER, values);
		return new CardOwnFIOFilter().accept(externalCard);
	}
}
