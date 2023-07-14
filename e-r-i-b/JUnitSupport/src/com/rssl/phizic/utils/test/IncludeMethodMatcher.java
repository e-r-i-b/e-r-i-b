package com.rssl.phizic.utils.test;

import com.rssl.phizic.utils.test.annotation.IncludeTest;

import java.lang.reflect.Method;

/**
 * @author Roshka
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class IncludeMethodMatcher extends TestAnnotationMatcherBase implements TestCaseMatcher<Method>
{
	public IncludeMethodMatcher(String configuration)
	{
		super(configuration);
	}

	public boolean match(Method method)
	{
		IncludeTest annotation = method.getAnnotation(IncludeTest.class);
		
		return match(annotation);
	}
}