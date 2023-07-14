package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.payments.forms.validators.AccountAndCardValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * @author eMakarov
 * @ created 25.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class CardAndClientCompatibles extends AccountAndCardValidatorBase
{
	private final static String FIELD_CARD_ID = "cardId";

	public CardAndClientCompatibles()
	{
		setMessage("Карта не принадлежит клиенту");
	}

	/**
	 * Проверяет значения полей на валидность ЭТОТ МЕТОД ВО ВСЕХ РЕАЛИЗАЦИЯХ ДОЛЖЕН БЫТЬ THREAD SAFE!!!!!!!!!
	 *
	 * @param values значения для проверки. Key - имя поля (в форме), Value - значение поля.
	 */
	public boolean validate(Map values) throws TemporalDocumentException
	{
		Card card;
		try
		{
			card = getCard(FIELD_CARD_ID, values);

			return getCardLink(card.getNumber()) != null;
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
	}
}
