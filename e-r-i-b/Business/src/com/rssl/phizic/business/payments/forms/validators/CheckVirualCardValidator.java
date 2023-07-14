package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.Card;

import java.util.Map;

/**
 * Проверяет, чтобы ресурс списания / зачисления не являлся виртуальной картой
 * @author Dorzhinov
 * @ created 28.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class CheckVirualCardValidator extends MultiFieldsValidatorBase
{
	private static String FIELD_RESOURCE = "resource";


	public boolean validate(Map values) throws TemporalDocumentException
	{
		Object resource = retrieveFieldValue(FIELD_RESOURCE, values);

		if (!(resource instanceof CardLink))
			return true;

		CardLink cardLink = (CardLink) resource;

		Card card = cardLink.getCard();

		return card != null && !card.isVirtual();
	}
}
