package com.rssl.phizic.utils.test.annotation;

import junit.framework.TestCase;
import com.rssl.phizic.config.ConfigurationContext;

import java.io.IOException;

/**
 * @author Roshka
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class MethodsAnnotaitionTest extends TestCase
{
	@IncludeTest(configurations = "sbrf")
	public void testInclude() throws IOException
	{
		ConfigurationContext configurationContext = ConfigurationContext.getIntstance();

		String activeConfiguration = configurationContext.getActiveConfiguration();
		assertTrue( activeConfiguration.equals("sbrf") );
	}

	@ExcludeTest(configurations = "russlav")
	public void testExclude() throws IOException
	{
		ConfigurationContext configurationContext = ConfigurationContext.getIntstance();

		assertTrue(!configurationContext.getActiveConfiguration().equals("russlav"));
	}

	public void testNothing() throws IOException
	{
	}
}