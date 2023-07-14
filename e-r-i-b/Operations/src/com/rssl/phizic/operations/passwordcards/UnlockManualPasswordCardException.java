package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Omeliyanchuk
 * @ created 24.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class UnlockManualPasswordCardException extends BusinessLogicException
{
	UnlockManualPasswordCardException()
	{
		super("Не возможно разблокировать карту, которую заблокировал сотрудник");
	}
}
