package com.rssl.phizic.utils.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author Roshka
 * @ created 21.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class TestSuiteCreator
{
	private TestCaseMatcher<Class<?>> classMatcher;
	private TestCaseMatcher<Method> methodMatcher;

	public TestSuiteCreator(TestCaseMatcher<Class<?>> classMatcher, TestCaseMatcher<Method> methodMatcher)
	{
		this.classMatcher = classMatcher;
		this.methodMatcher = methodMatcher;
	}

	/**
	 * Динамическое создание TestSuite
	 *
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public TestSuite create() throws IOException, ClassNotFoundException
	{
		Collection<Class<?>> tests = loadTests();
		Collections.sort( new ArrayList<Class<?>>(tests), new ClassDependenciesComparator() );

		TestSuite suite = new TestSuite();

		for (Class<?> clazz : tests)
			suite.addTest(createSuite(clazz));

		return suite;
	}

	/**
	 * Сбор и сортировка тестов.
	 *
	 * @return коллекция тестов
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private Collection<Class<?>> loadTests() throws ClassNotFoundException, IOException
	{
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();

		JUnitTestCollector testCollector = new JUnitTestCollector();
		Enumeration enumeration = testCollector.collectTests();
		while (enumeration.hasMoreElements())
		{
			String className = (String) enumeration.nextElement();
			Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);

			if (classMatcher.match(clazz))
				classMap.put(clazz.getSimpleName(), clazz);
		}

		return new TreeMap<String, Class<?>>(classMap).values();
	}

	private TestSuite createSuite(final Class theClass)
	{
		TestSuite suite = new TestSuite();
		suite.setName(theClass.getName());

		try
		{
			TestSuite.getTestConstructor(theClass); // Avoid generating multiple error messages
		}
		catch (NoSuchMethodException e)
		{
			suite.addTest(warning("Class " + theClass.getName() + " has no public constructor TestCase(String name) or TestCase()"));
			return suite;
		}

		if (!Modifier.isPublic(theClass.getModifiers()))
		{
			suite.addTest(warning("Class " + theClass.getName() + " is not public"));
			return suite;
		}

		Class superClass = theClass;
		List<String> names = new ArrayList<String>();
		while (Test.class.isAssignableFrom(superClass))
		{
			Method[] methods = superClass.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++)
			{
				addTestMethod(suite, methods[i], names, theClass);
			}
			superClass = superClass.getSuperclass();
		}
		if (suite.countTestCases() == 0)
			suite.addTest(warning("No tests found in " + theClass.getName()));

		return suite;
	}

	private void addTestMethod(TestSuite suite, Method method, List<String> names, Class theClass)
	{
		String name = method.getName();
		if (names.contains(name))
			return;

//		if (!isPublicTestMethod(m))
//		{
//			if (isTestMethod(m))
//				suite.addTest(warning("Test method isn't public: " + m.getName()));
//			return;
//		}

		if (methodMatcher.match(method))
		{
			names.add(name);
			Test test = TestSuite.createTest(theClass, name);
			suite.addTest(test);
		}
	}

	private static Test warning(final String message)
	{
		return new TestCase("warning")
		{
			protected void runTest()
			{
				fail(message);
			}
		};
	}

}