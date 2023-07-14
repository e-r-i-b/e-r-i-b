package com.rssl.phizic.utils.test;

import com.rssl.phizic.utils.test.annotation.ClassTest;
import com.rssl.phizic.utils.test.annotation.MethodTest;
import com.rssl.phizic.utils.StringUtils;

import java.lang.reflect.Method;

/**
 * User: Balovtsev
 * Date: 22.02.2012
 * Time: 15:33:04
 */
public class MethodTestMatcher implements TestCaseMatcher<Method>
{
	private String configuration;

	public MethodTestMatcher(String configuration)
	{
		this.configuration = configuration;
	}

	public boolean match(Method method)
	{
		if (!method.getDeclaringClass().isAnnotationPresent(ClassTest.class))
		{
			return false;
		}

		switch (method.getDeclaringClass().getAnnotation(ClassTest.class).excludeType())
		{
			case NONE:
			{
				return true;
			}

			case ALL_ANNOTATED:
			{
				return !method.isAnnotationPresent(MethodTest.class);
			}

			case DETERMINED_BY_METHOD_ANNOTATION:
			{
				if (method.isAnnotationPresent(MethodTest.class))
				{
					MethodTest methodTest = method.getAnnotation(MethodTest.class);
					if (methodTest.exclude())
					{
						return false;
					}

					if (StringUtils.contains(methodTest.configurations(), configuration))
					{
						return false;
					}
				}
				return true;
			}
		}

		return false;
	}
}