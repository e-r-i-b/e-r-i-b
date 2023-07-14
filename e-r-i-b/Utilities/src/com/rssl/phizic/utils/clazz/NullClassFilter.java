package com.rssl.phizic.utils.clazz;

/**
 * @author Evgrafov
 * @ created 31.03.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1055 $
 */
public final class NullClassFilter implements ClassFilter
{
	public static final NullClassFilter INSTANCE = new NullClassFilter();

	private NullClassFilter()
	{
	}

	public boolean accept(Class clazz)
	{
		return true;
	}
}