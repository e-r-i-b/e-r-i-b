package com.rssl.phizic.utils.test;

import java.lang.reflect.Method;

/**
 * @author Roshka
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class TestMethodMatcher implements TestCaseMatcher<Method>
{
	public boolean match(Method method)
	{
		String name = method.getName();
		Class[] parameters = method.getParameterTypes();
		Class returnType = method.getReturnType();

		return parameters.length == 0 && name.startsWith("test") && returnType.equals(Void.TYPE);
	}
}