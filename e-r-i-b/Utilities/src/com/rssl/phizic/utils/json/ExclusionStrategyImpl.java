package com.rssl.phizic.utils.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.rssl.phizic.common.types.annotation.JsonExclusion;

/**
 * @author Moshenko
 * @ created 05.02.2014
 * @ $Authors$
 * @ $Revision$
 */
public class ExclusionStrategyImpl implements ExclusionStrategy
{
	public boolean shouldSkipField(FieldAttributes f)
	{
		return f.getAnnotation(JsonExclusion.class) != null;
	}

	public boolean shouldSkipClass(Class<?> clazz)
	{
		return clazz.getAnnotation(JsonExclusion.class) != null;
	}
}
