package com.rssl.phizic.business.loanclaim.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.gate.loanclaim.type.CardKind;

/**
 * Валидирует поле тип карты. Возвращает true, если значение EMPTY
 * @author Rtischeva
 * @ created 08.04.14
 * @ $Author$
 * @ $Revision$
 */
public class CardTypeRequiredValidator extends FieldValidatorBase
{
	public CardTypeRequiredValidator()
	{
		this("Поле Тип обязательно для заполнения.");
	}

	public CardTypeRequiredValidator(String message)
	{
		setMessage(message);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (CardKind.EMPTY.name().equals(value))
			return false;
		return true;
	}
}
