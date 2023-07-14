package com.rssl.phizic.business.persons;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author osminin
 * @ created 03.04.2009
 * @ $Author$
 * @ $Revision$
 */

public class PropertiesDataValidateConfig extends DataValidateConfig
{
	private String phoneFormatRegexp;
	private String phoneFormatDescription;

	public PropertiesDataValidateConfig(PropertyReader propertyReader)
	{
		super(propertyReader);
	}

	public String getPhoneFormatRegexp()
	{
		return phoneFormatRegexp;
	}

	public String getPhoneFormatDescription()
	{
		return phoneFormatDescription;
	}

	public void doRefresh() throws ConfigurationException
	{
		phoneFormatRegexp      = getProperty(DataValidateConfig.PHONE_FORMAT_REGEXP);
		phoneFormatDescription = getProperty(DataValidateConfig.PHONE_FORMAT_DESCRIPTION);
	}
}
