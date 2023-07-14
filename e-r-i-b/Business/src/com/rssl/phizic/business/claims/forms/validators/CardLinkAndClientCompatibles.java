package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.phizic.business.payments.forms.validators.AccountAndCardValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * @author Gulov
 * @ created 15.07.2010
 * @ $Authors$
 * @ $Revision$
 */
public class CardLinkAndClientCompatibles extends AccountAndCardValidatorBase
{
	private final static String FIELD_CARD_ID = "cardId";

	public CardLinkAndClientCompatibles()
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
		return getCardLink(FIELD_CARD_ID, values) != null;
	}
}
