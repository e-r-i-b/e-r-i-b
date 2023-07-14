package com.rssl.auth.csa.back.servises.restrictions.security;

import com.rssl.auth.csa.back.servises.restrictions.Restriction;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.RestrictionException;

/**
 * @author krenev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 * Ограничение на НЕ соотвествие строки логину ipas. ()
 */
public class NotIpasLoginRestriction implements Restriction<String>
{
	private String message;

	public NotIpasLoginRestriction(String message)
	{
		this.message = message;
	}

	public void check(String object) throws Exception
	{
		if (Utils.isIPasLogin(object))
		{
			throw new RestrictionException(message);
		}
	}
}
