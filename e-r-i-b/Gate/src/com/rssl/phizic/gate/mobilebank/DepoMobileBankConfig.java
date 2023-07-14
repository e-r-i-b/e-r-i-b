package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.config.*;

/**
 * User: Moshenko
 * Date: 05.09.13
 * Time: 15:10
 */
public class DepoMobileBankConfig extends Config
{
	private static final String MBV_URL = "mbv.webservice.url";
	private static final String USE_LOGGER = "mbv.logger.use";
	private static final String AVALIABILITY = "mbv.availability";

	public DepoMobileBankConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return признак доступности MBV
	 */
	public boolean isMbvAvaliability()
	{
		return getBoolProperty(AVALIABILITY);
	}

	/**
	 * @return endpoint url веб сервися mbv
	 */
	public String getEndpointUrl()
	{
		return getProperty(MBV_URL);
	}

	/**
	 * @return endpoint url веб сервися mbv
	 */
	public boolean isMbvLoggerUse()
	{
		return getBoolProperty(USE_LOGGER);
	}

	public void doRefresh() throws ConfigurationException
	{
	}

}
