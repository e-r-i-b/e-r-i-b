package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentLogicException;

/**
 * @author Omeliyanchuk
 * @ created 02.05.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * —чет заблокирован
 */
public class AccountBlockedException extends DocumentLogicException
{
	AccountBlockedException(String message)
	{
		super(message);
	}
}
