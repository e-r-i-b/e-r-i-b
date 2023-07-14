package com.rssl.phizic.utils.test;

import com.rssl.phizic.utils.test.annotation.ExcludeTest;
import com.rssl.phizic.utils.test.annotation.IncludeTest;

/**
 * @author Roshka
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class TestAnnotationMatcherBase
{
	private String configuration;
	private ConfigurationAnnotationHelper helper;

	public TestAnnotationMatcherBase(String configuration)
	{
		this.configuration = configuration;
		this.helper = new ConfigurationAnnotationHelper();
	}

	public boolean match(ExcludeTest annotation)
	{
		return helper.match(configuration, annotation);
	}

	public boolean match(IncludeTest annotation)
	{
		return helper.match(configuration, annotation);
	}
}