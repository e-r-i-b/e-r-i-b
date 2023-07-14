package com.rssl.phizic.utils.test;

import com.rssl.phizic.utils.test.annotation.ExcludeTest;

import java.lang.reflect.Method;

/**
 * @author Roshka
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class ExcludeMethodMatcher extends TestAnnotationMatcherBase implements TestCaseMatcher<Method>
{
	public ExcludeMethodMatcher(String configuration)
	{
		super(configuration);
	}

	public boolean match(Method method)
	{
		return match(method.getAnnotation(ExcludeTest.class));
	}
}