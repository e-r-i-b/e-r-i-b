package com.rssl.phizic.utils.test;

import java.lang.reflect.Modifier;

/**
 * @author Roshka
 * @ created 24.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class ExcludeAbstractTestMatcher implements TestCaseMatcher<Class<?>>
{
	public boolean match(Class<?> clazz)
	{
		if (Modifier.isAbstract(clazz.getModifiers()))
			return false;
		return true;
	}
}