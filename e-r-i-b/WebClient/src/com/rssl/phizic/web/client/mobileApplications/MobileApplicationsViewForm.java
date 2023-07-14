package com.rssl.phizic.web.client.mobileApplications;

import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @ author: Gololobov
 * @ created: 04.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileApplicationsViewForm extends EditFormBase
{
	private Long cancelId;
	//Список подключенных мобильных приложений
	private List<ConnectorInfo> mobileApplications;
	//Список доступных для отображения мобильных приложений
	private List<MobilePlatform> mobilePlatformList;

	public Long getCancelId()
	{
		return cancelId;
	}

	public void setCancelId(Long cancelId)
	{
		this.cancelId = cancelId;
	}

	public List<ConnectorInfo> getMobileApplications()
	{
		return mobileApplications;
	}

	public void setMobileApplications(List<ConnectorInfo> mobileApplications)
	{
		this.mobileApplications = mobileApplications;
	}

	public List<MobilePlatform> getMobilePlatformList()
	{
		return mobilePlatformList;
	}

	public void setMobilePlatformList(List<MobilePlatform> mobilePlatformList)
	{
		this.mobilePlatformList = mobilePlatformList;
	}
}
