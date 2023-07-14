package com.rssl.phizic.web.client.socialApplications;

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
public class SocialApplicationsViewForm extends EditFormBase
{
	private Long cancelId;
	//Список подключенных социальных приложений
	private List<ConnectorInfo> socialApplications;
	//Список доступных для отображения социальных приложений
	private List<MobilePlatform> socialPlatformList;

	public Long getCancelId()
	{
		return cancelId;
	}

	public void setCancelId(Long cancelId)
	{
		this.cancelId = cancelId;
	}

	public List<ConnectorInfo> getSocialApplications()
	{
		return socialApplications;
	}

	public void setSocialApplications(List<ConnectorInfo> socialApplications)
	{
		this.socialApplications = socialApplications;
	}

	public List<MobilePlatform> getSocialPlatformList()
	{
		return socialPlatformList;
	}

	public void setSocialPlatformList(List<MobilePlatform> socialPlatformList)
	{
		this.socialPlatformList = socialPlatformList;
	}
}
