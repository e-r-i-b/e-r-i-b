package com.rssl.phizic.utils.test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Roshka
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class PublicMethodMatcher implements TestCaseMatcher<Method>
{
	public boolean match(Method method)
	{
		return Modifier.isPublic(method.getModifiers());
	}
}