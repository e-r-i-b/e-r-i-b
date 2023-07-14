package com.rssl.phizic.utils.test;

import junit.runner.LoadingTestCollector;

/**
 * @author Evgrafov
 * @ created 20.12.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3094 $
 */

public class JUnitTestCollector extends LoadingTestCollector
{

	protected boolean isTestClass(String classFileName)
	{
		if(classFileName.contains("generated"))
			return false;

		return super.isTestClass(classFileName);
	}
}