package com.rssl.phizic.authgate.csa;

import com.rssl.phizic.authgate.AuthConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Gainanov
 * @ created 21.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class CSAConfig extends Config implements AuthConfig
{

	public static final String WAY4_SERVICE_URL = "way4.webservice.url";
	public static final String IPAS_CAP_SERVICE_URL = "iPas.cap.webservice.url";
	public static final String CSA_BACK_URL = "csa.back.webservice.url";

	private static final Properties properties = readProperties();

	public CSAConfig(PropertyReader reader)
	{
		super(reader);
	}

	private static Properties readProperties()
	{
		Properties temp = new Properties(System.getProperties());
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream("csa.properties");

		try
		{
			temp.load(stream);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return temp;
	}

	/**
	 * @return url way4 service.
	 */
	public String getWay4ServiceUrl()
	{
		return properties.getProperty(WAY4_SERVICE_URL);
	}

	/**
	 * @return url ipas cap service.
	 */
	public String getiPasCapServiceUrl()
	{
		return properties.getProperty(IPAS_CAP_SERVICE_URL);
	}

	public void doRefresh()
	{
	}
}
