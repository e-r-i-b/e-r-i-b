package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * @author eMakarov
 * @ created 06.02.2008
 * @ $Author$
 * @ $Revision$
 */
public class CardStateEqualToOneFromParametersValidator extends EqualToOneFromParametersValidator
{
	private final static String FIELD_CARD_ID = "cardId";

	public CardStateEqualToOneFromParametersValidator()
	{
		super();
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Card card = getCard(FIELD_CARD_ID, values);

		// чтобы не повторять код validate() класса EqualToOneFromParametersValidator
		setBinding(FIELD_PARAMETER_NAME, FIELD_PARAMETER_NAME);
		values.put(FIELD_PARAMETER_NAME, card.getCardState().toString());
		return super.validate(values);
	}
}
