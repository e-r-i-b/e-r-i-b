package com.rssl.phizic.business.dictionaries.pfp;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author bogdanov
 * @ created 23.01.14
 * @ $Author$
 * @ $Revision$
 */

public class PfpConfig extends Config
{
	protected static final String RESOURCES_PATH = "resources.path";
	private static final String YEAR_COUNT_KEY = "max.planning.year";
	private String resourcePath;
	private int yearCount;

	public PfpConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		resourcePath = getProperty(RESOURCES_PATH);
		yearCount = getIntProperty(YEAR_COUNT_KEY);
	}

	public String getResourcePath()
	{
		return resourcePath;
	}

	public int getYearCount()
	{
		return yearCount;
	}
}
