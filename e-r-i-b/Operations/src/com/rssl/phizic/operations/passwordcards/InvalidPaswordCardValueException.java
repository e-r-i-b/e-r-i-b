package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Omeliyanchuk
 * @ created 24.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class InvalidPaswordCardValueException extends BusinessLogicException
{
	InvalidPaswordCardValueException()
	{
		super("Неправильный пароль");
	}
}
