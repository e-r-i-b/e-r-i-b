package com.rssl.phizic.operations.passwords;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Omeliyanchuk
 * @ created 27.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class InvalidNewCardNumberException extends BusinessLogicException
{
	public InvalidNewCardNumberException()
	{
		super("Неверный номер карточки ключей");
	}
}
