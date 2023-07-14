package com.rssl.phizic.utils.test;

import com.rssl.phizic.config.ConfigurationContext;
import com.rssl.phizic.utils.test.annotation.ExcludeTest;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author Roshka
 * @ created 21.11.2006
 * @ $Author$
 * @ $Revision$
 */

@ExcludeTest
public class ConfigurableTestRunner extends TestCase
{
	public static TestSuite suite() throws IOException, ClassNotFoundException
	{
		String configurationName = ConfigurationContext.getIntstance().getActiveConfiguration();
		String packageName       = System.getProperty("test.package");

		CompositeTestCaseMatcher<Class<?>> classMatcher =
				new CompositeTestCaseMatcher<Class<?>>(
						new PackageMatcher(packageName),
						new ExcludeAbstractTestMatcher(),
						new ClassTestMatcher(configurationName)
						);

		CompositeTestCaseMatcher<Method> methodMatcher =
				new CompositeTestCaseMatcher<Method>(
						new PublicMethodMatcher(),
						new TestMethodMatcher(),
						new MethodTestMatcher(configurationName)
				);

		TestSuiteCreator creater = new TestSuiteCreator(classMatcher, methodMatcher);

		return creater.create();
	}

}