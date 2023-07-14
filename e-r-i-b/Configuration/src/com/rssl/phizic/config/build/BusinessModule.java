package com.rssl.phizic.config.build;

import org.apache.commons.lang.StringUtils;
import com.rssl.phizic.config.build.generated.BusinessModuleDescriptor;

/**
 * @author Erkin
 * @ created 08.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class BusinessModule extends Module
{
	private final String jarName;

	BusinessModule(BusinessModuleDescriptor desc)
	{
		super(desc);
		this.jarName = StringUtils.defaultIfEmpty(desc.getJarName(), getName() + ".jar");
	}

	/**
	 * @return טלÿ jar-פאיכא
	 */
	public String getJarName()
	{
		return jarName;
	}
}
