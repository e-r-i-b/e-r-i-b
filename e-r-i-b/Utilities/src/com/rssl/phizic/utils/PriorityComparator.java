package com.rssl.phizic.utils;

import java.util.Comparator;

/**
 * @author Erkin
 * @ created 06.11.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Компаратор на основе сравнения приоритета объектов
 */
public abstract class PriorityComparator<O> implements Comparator<O>
{
	public final int compare(O object1, O object2)
	{
		Priority priority1 = getPriority(object1);
		Priority priority2 = getPriority(object2);
		return priority1.compareTo(priority2);
	}

	protected abstract Priority getPriority(O object);
}
