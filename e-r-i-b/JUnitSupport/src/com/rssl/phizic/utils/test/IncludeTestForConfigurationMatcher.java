package com.rssl.phizic.utils.test;

import com.rssl.phizic.utils.test.annotation.IncludeTest;

/**
 * @author Roshka
 * @ created 24.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class IncludeTestForConfigurationMatcher extends TestAnnotationMatcherBase implements TestCaseMatcher<Class<?>>
{
	public IncludeTestForConfigurationMatcher(String configuration)
	{
		super(configuration);
	}

	public boolean match(Class<?> clazz)
	{
		IncludeTest includeTest = clazz.getAnnotation(IncludeTest.class);

		return match(includeTest);
	}
}