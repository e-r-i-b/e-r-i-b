package com.rssl.phizic.appinfo;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfoExtractor;

/**
 * @author bogdanov
 * @ created 25.07.2013
 * @ 
 * @ 
 */

public class ApplicationInfo implements ApplicationInfoExtractor
{
	public Application getApplication()
	{
		return Application.Other;
	}
}