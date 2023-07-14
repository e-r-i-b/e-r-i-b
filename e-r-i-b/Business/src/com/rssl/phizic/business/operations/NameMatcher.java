package com.rssl.phizic.business.operations;

import java.lang.reflect.Method;

/**
 * ѕровер€ет соответствие метода его имени
 * @author Evgrafov
 * @ created 14.05.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4221 $
 */
public class NameMatcher implements MethodMatcher
{
	private String name;

	public NameMatcher(String name)
	{
		if(name == null)
			throw new NullPointerException("name");
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public boolean match(Method method)
	{
		return name.equals(method.getName());
	}
}