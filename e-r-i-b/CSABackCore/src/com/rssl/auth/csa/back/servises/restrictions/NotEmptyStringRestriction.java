package com.rssl.auth.csa.back.servises.restrictions;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.auth.csa.back.exceptions.RestrictionException;

/**
 * @author krenev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 * Ограничение на непустоту строки.
 */
public class NotEmptyStringRestriction implements Restriction<String>
{
	private String message;

	public NotEmptyStringRestriction(String message)
	{
		this.message = message;
	}

	public void check(String object) throws Exception
	{
		if (StringHelper.isEmpty(object))
		{
			throw new RestrictionException(message);
		}
	}
}
