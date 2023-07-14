package com.rssl.phizic.utils.test.annotation;

import com.rssl.phizic.utils.test.IncludeTestForConfigurationMatcher;
import com.rssl.phizic.utils.test.ExcludeTestForConfigurationMatcher;
import junit.framework.TestCase;

import java.io.IOException;

/**
 * @author Roshka
 * @ created 24.11.2006
 * @ $Author$
 * @ $Revision$
 */

@ExcludeTest(configurations = "sbrf")
@IncludeTest(configurations = "russlav")
public class TestClassAnnotationsTest extends TestCase
{
	public void testIncluded() throws IOException, ClassNotFoundException
	{
		IncludeTestForConfigurationMatcher filter = new IncludeTestForConfigurationMatcher("russlav");
		boolean isMatchedForRusslav = filter.match(TestClassAnnotationsTest.class);

    	assertTrue(isMatchedForRusslav);

		IncludeTestForConfigurationMatcher rubbishFilter =
				new IncludeTestForConfigurationMatcher("wiourfguiowr gierufgi erug");
		boolean isIncluded = rubbishFilter.match(TestClassAnnotationsTest.class);
		assertFalse(isIncluded);
	}

	public void testExcluded() throws IOException, ClassNotFoundException
	{
		ExcludeTestForConfigurationMatcher filter = new ExcludeTestForConfigurationMatcher("sbrf");
		boolean isMatchedForSbrf = filter.match(TestClassAnnotationsTest.class);
		assertFalse(isMatchedForSbrf);
		ExcludeTestForConfigurationMatcher rubbishFilter =
				new ExcludeTestForConfigurationMatcher("wiourfguiowr gierufgi erug");
		boolean isExcluded = rubbishFilter.match(TestClassAnnotationsTest.class);
		assertTrue(isExcluded);
	}
}
