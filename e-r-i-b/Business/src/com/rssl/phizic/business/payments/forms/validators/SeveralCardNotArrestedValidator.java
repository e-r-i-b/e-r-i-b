package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.NotArrestedCardFilter;
import com.rssl.phizic.utils.MaskUtil;

import java.util.Map;

/**
 * Проверка своей карты на возможность ареста.
 *
 * @author khudyakov
 * @ created 15.11.14
 * @ $Author$
 * @ $Revision$
 */
public class SeveralCardNotArrestedValidator extends AccountAndCardValidatorBase
{
	private static final String SEVERAL_CARD_NUMBER_PARAMETER_NAME  = "cardNumber";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String cardNumber = (String) retrieveFieldValue(SEVERAL_CARD_NUMBER_PARAMETER_NAME, values);

		try
		{
			CardLink cardLink = getCardLink(cardNumber);
			if (cardLink == null)
			{
				throw new TemporalDocumentException("Карта № " + MaskUtil.getCutCardNumber(cardNumber) + " не найдена.");
			}
			return new NotArrestedCardFilter().accept(cardLink.getCard());
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
	}
}
