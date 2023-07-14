package com.rssl.phizic.appinfo;

import com.rssl.phizic.common.types.ApplicationInfoExtractor;
import com.rssl.phizic.common.types.Application;

/**
 * @author bogdanov
 * @ created 25.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class ApplicationInfo implements ApplicationInfoExtractor
{
	public Application getApplication()
	{
		return Application.Other;
	}
}
