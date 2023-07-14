package com.rssl.phizic.business.loanclaim.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.gate.loanclaim.type.DepositKind;

/**
 * Валидирует поле тип счета. Возвращает true, если значение EMPTY
 * @author Rtischeva
 * @ created 08.04.14
 * @ $Author$
 * @ $Revision$
 */
public class AccountTypeRequiredValidator extends FieldValidatorBase
{
	public AccountTypeRequiredValidator()
	{
		this("Поле Тип обязательно для заполнения.");
	}

	public AccountTypeRequiredValidator(String message)
	{
		setMessage(message);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (DepositKind.EMPTY.name().equals(value))
			return false;
		return true;
	}
}
