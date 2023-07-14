package com.rssl.phizic.authgate.csa;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * ��������������� ������ ��� CSA
 * @author basharin
 * @ created 06.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class CSARefreshConfig extends Config
{
	public static final String CSA_AUTH_TIMEOUT = "csa.auth.service.timeout";
	public static final String CSA_IPAS_TIMEOUT = "csa.ipas.service.timeout";
	public static final String CSA_SERVICE_URL = "csa.webservice.url";
	private static final String COOKIE_LIFE_TIME = "com.rssl.auth.csa.front.config.cookie.life.time";
	private static final String REGION_SELECTION_FUNCTIONALITY = "csa.region.selection.functionality.on";

	private static final String SBERBANK_WEBSITE_PREFIX = "sberbank.website.prefix";

	private int csaAuthTimeout;
	private int csaIPasTimeout;
	private String csaServiceUrl;
	private String sberbankWebsitePrefix;

	private Integer cookieLifeTime;
	private Boolean regionSelectionFunctionality;

	public CSARefreshConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		csaAuthTimeout = getIntProperty(CSA_AUTH_TIMEOUT);
		csaIPasTimeout = getIntProperty(CSA_IPAS_TIMEOUT);
		csaServiceUrl = getProperty(CSA_SERVICE_URL);

		cookieLifeTime = getIntProperty(COOKIE_LIFE_TIME);
		regionSelectionFunctionality = getBoolProperty(REGION_SELECTION_FUNCTIONALITY);
		sberbankWebsitePrefix = getProperty(SBERBANK_WEBSITE_PREFIX);

	}

	/**
	 * @return ����� �������� ������ � �������������
	 */
	public Integer getAuthTimeout()
	{
		return csaAuthTimeout;
	}

	/**
	 * @return ����� �������� ������ � ������������� ��� IPas
	 */
	public Integer getIPasTimeout()
	{
		return csaIPasTimeout;
	}

	/**
	 * @return ����� web-������� �� "CSA"
	 */
	public String getCsaServiceUrl()
	{
		return csaServiceUrl;
	}

	/**
	 * ����� ����� ����(���)
	 * @return ���-�� ����
	 */
	public int getCookieLifeTime()
	{
		return cookieLifeTime;
	}

	/**
	 * ���������� ��������� ���������������� ������ �������
	 * @return true(��������)/false(���������)
	 */
	public boolean getRegionSelectionFunctionalityMode()
	{
		return regionSelectionFunctionality;
	}

	/**
	 * @return ������� ����� �����
	 */
	public String getSberbankWebsitePrefix()
	{
		return sberbankWebsitePrefix;
	}
}
