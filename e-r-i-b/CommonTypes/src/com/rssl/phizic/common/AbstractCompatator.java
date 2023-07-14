package com.rssl.phizic.common;

import java.util.Comparator;

/**
 * @author Krenev
 * @ created 13.12.2007
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractCompatator<T> implements Comparator<T>
{
	protected boolean isObjectsEquals(Object o1, Object o2)
	{
//можно заюзать ^, но выражение получается .....:)
		if (o1 == null && o2 == null)
		{
			return true;
		}
		if (o1 == null || o2 == null)
		{
			return false;
		}
		return o1.equals(o2);
	}
}
