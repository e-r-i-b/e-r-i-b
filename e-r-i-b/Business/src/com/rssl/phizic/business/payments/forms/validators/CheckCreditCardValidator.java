package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardType;

import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 27.11.2011
 * @ $Author$
 * @ $Revision$
 *
 * Проверяет, чтобы ресурс списания / зачисления не являлся кредитной картой
 */

public class CheckCreditCardValidator extends MultiFieldsValidatorBase
{
	private static String FIELD_RESOURCE = "resource";
	

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Object resource = retrieveFieldValue(FIELD_RESOURCE, values);

		if (!(resource instanceof CardLink))
			return true;

		CardLink  cardLink = (CardLink) resource;

		Card card = cardLink.getCard();

		return card != null && card.getCardType() != CardType.credit;
	}
}
