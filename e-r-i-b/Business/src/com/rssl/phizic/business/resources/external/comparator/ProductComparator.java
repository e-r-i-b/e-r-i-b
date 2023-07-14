package com.rssl.phizic.business.resources.external.comparator;

/**
 * @author lukina
 * @ created 13.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class ProductComparator
{
	protected int compareToNull(Object o1, Object o2)
	{
		if (o1 == null && o2 == null)
		{
			return 0;
		}
		if (o1 == null && o2 != null)
		{
			return -1;
		}
		if (o1 != null && o2 == null)
		{
			return 1;
		}
		throw new IllegalArgumentException("Хотя бы один из парамектров должен быть null");
	}
}
