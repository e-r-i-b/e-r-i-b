package com.rssl.phizic.web.common.socialApi.devicecheck;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Dorzhinov
 * @ created 04.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class DeviceCheckForm extends ActionFormBase
{
    /**
     * in
     */
    private String appType; // тип приложения (iPhone, iPad, android)
	private Long appVersion; // версия ПО
    private String reason; //причина проверки

    /**
     * out
     */
    private boolean jailbreakEnabled;

	public String getAppType()
	{
		return appType;
	}

	public void setAppType(String appType)
	{
		this.appType = appType;
	}

	public Long getAppVersion()
	{
		return appVersion;
	}

	public void setAppVersion(Long appVersion)
	{
		this.appVersion = appVersion;
	}

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public boolean getJailbreakEnabled()
    {
        return jailbreakEnabled;
    }

    public void setJailbreakEnabled(boolean jailbreakEnabled)
    {
        this.jailbreakEnabled = jailbreakEnabled;
    }
}
