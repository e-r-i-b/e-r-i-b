package com.rssl.phizic.web.common.socialApi.version;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author rydvanskiy
 * @ created 06.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class VersionControlForm extends ActionFormBase
{
    // ��� ���������� � ������ ��������� 1.04 �������� ������ iDevice
	private String appType;
	// ������ ��
	private Long appVersion;

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
}
