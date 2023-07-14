package com.rssl.phizic.utils;

import java.util.Map;
import java.util.HashMap;

/**
 * @author egorova
 * @ created 02.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class BeanFormatterMap
{
	private static final Map<Object, Class<? extends BeanFormatter>> map= new HashMap<Object, Class<? extends BeanFormatter>>();

	public static Map getMap()
	{
		return map;
	}

	static
	{
		map.put(java.lang.Class.class, com.rssl.phizic.utils.ClassHelper.class);
		map.put(java.lang.Enum.class, com.rssl.phizic.utils.EnumServiceHelper.class);
	}
}
