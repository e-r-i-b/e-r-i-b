package com.rssl.auth.csa.back.servises.restrictions.security;

/**
 * @author Jatsky
 * @ created 13.01.14
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.restrictions.Restriction;

/**
 * @author krenev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 * Ограничение на НЕ соотвествие строки временному логину.
 */
public class NotDisposableLoginRestriction implements Restriction<String>
{
	private String message;

	public NotDisposableLoginRestriction(String message)
	{
		this.message = message;
	}

	public void check(String object) throws Exception
	{
		if (Utils.isDisposableLogin(object))
		{
			throw new RestrictionException(message);
		}
	}
}
