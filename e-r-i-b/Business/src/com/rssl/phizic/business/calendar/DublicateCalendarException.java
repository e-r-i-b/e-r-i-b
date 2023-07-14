package com.rssl.phizic.business.calendar;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author niculichev
 * @ created 15.03.2010
 * @ $Author$
 * @ $Revision$
 */

public class DublicateCalendarException extends BusinessLogicException
{
	public DublicateCalendarException(Throwable cause)
	{
		super(" алендарь с таким именем уже существует.", cause);
	}

	public DublicateCalendarException(String str, Throwable cause)
	{
		super(str, cause);
	}
}
