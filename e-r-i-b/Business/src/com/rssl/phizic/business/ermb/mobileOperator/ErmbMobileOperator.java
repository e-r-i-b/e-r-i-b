package com.rssl.phizic.business.ermb.mobileOperator;

/**
 * Оператор сотовой связи в контексте интеграции с ЕРМБ
 * @author Puzikov
 * @ created 11.03.15
 * @ $Author$
 * @ $Revision$
 */

public class ErmbMobileOperator
{
	private Long mnc;
	private String name;
	private boolean useIntegration;
	private String serviceUrl;
	private String serviceLogin;
	private String servicePassword;

	public Long getMnc()
	{
		return mnc;
	}

	public void setMnc(Long mnc)
	{
		this.mnc = mnc;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isUseIntegration()
	{
		return useIntegration;
	}

	public void setUseIntegration(boolean useIntegration)
	{
		this.useIntegration = useIntegration;
	}

	public String getServiceUrl()
	{
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl)
	{
		this.serviceUrl = serviceUrl;
	}

	public String getServiceLogin()
	{
		return serviceLogin;
	}

	public void setServiceLogin(String serviceLogin)
	{
		this.serviceLogin = serviceLogin;
	}

	public String getServicePassword()
	{
		return servicePassword;
	}

	public void setServicePassword(String servicePassword)
	{
		this.servicePassword = servicePassword;
	}
}
