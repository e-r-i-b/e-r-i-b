package test.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.runner.LoadingTestCollector;

import java.util.Enumeration;

/**
 * @author Roshka
 * @ created 21.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class ConfigTest extends TestCase
{
	public void test()
	{
		LoadingTestCollector testCollector = new LoadingTestCollector();
		Enumeration enumeration = testCollector.collectTests();

		assertNotNull(enumeration);

		while (enumeration.hasMoreElements())
		{
			Test test = (Test) enumeration.nextElement();

		}
		System.out.println("config test");
	}


	public static Test suite() throws Throwable
	{
		TestSuite suite = new TestSuite(ConfigTest.class);
		return suite;
	}
}