package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Omeliyanchuk
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class AccountAlreadyExistsException extends BusinessLogicException
{
	public AccountAlreadyExistsException()
	{
		super("—чет уже добавлен в систему");
	}
}
