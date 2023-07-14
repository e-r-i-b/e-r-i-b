package com.rssl.phizic.utils.test;

import com.rssl.phizic.utils.test.annotation.ExcludeTest;

/**
 * @author Roshka
 * @ created 24.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class ExcludeTestForConfigurationMatcher extends TestAnnotationMatcherBase implements TestCaseMatcher<Class<?>>
{
	public ExcludeTestForConfigurationMatcher(String configuration)
	{
		super(configuration);
	}

	public boolean match(Class<?> clazz)
	{
		ExcludeTest excludeTest = clazz.getAnnotation(ExcludeTest.class);

		return match(excludeTest);
	}
}