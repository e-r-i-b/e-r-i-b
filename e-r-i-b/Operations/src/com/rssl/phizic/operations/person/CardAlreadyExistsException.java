package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Omeliyanchuk
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class CardAlreadyExistsException extends BusinessLogicException
{
	public CardAlreadyExistsException()
	{
		super("Карта уже добавлена в систему");
	}
}
