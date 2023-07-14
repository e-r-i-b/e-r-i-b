package com.rssl.phizic.web.client.asyncwebpageform;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * User: moshenko
 * Date: 03.12.12
 * Time: 0:42
 */
public class WebPageForm extends ActionFormBase
{
	//id скина
	private Long skinId;
	private  String sideMenuLocation;

	public Long getSkinId()
	{
		return skinId;
	}

	public void setSkinId(Long skinId)
	{
		this.skinId = skinId;
	}

	public String getSideMenuLocation()
	{
		return sideMenuLocation;
	}

	public void setSideMenuLocation(String sideMenuLocation)
	{
		this.sideMenuLocation = sideMenuLocation;
	}
}
