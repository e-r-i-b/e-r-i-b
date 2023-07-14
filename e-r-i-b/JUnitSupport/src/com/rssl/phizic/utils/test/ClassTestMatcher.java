package com.rssl.phizic.utils.test;

import com.rssl.phizic.utils.test.annotation.ClassTest;
import com.rssl.phizic.utils.StringUtils;

/**
 * User: Balovtsev
 * Date: 22.02.2012
 * Time: 15:43:29
 */
public class ClassTestMatcher implements TestCaseMatcher<Class>
{
	private String configuration;

	/**
	 *
	 * @param configuration
	 */
	public ClassTestMatcher(String configuration)
	{
		this.configuration = configuration;
	}

	public boolean match(Class clazz)
	{
		if (!clazz.isAnnotationPresent(ClassTest.class))
		{
			return false;
		}

		ClassTest annotation = (ClassTest) clazz.getAnnotation(ClassTest.class);
		if (annotation.exclude())
		{
			return false;
		}

		if (StringUtils.contains(annotation.configurations(), configuration))
		{
			return false;
		}

		return true;
	}
}
