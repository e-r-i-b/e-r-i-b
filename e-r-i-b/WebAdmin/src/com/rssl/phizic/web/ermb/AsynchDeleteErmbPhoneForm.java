package com.rssl.phizic.web.ermb;

import com.rssl.phizic.web.actions.ActionFormBase;

public class AsynchDeleteErmbPhoneForm extends ActionFormBase
{
	private String profileId;
	private String phoneCode;

	public String getProfileId()
	{
		return profileId;
	}

	public void setProfileId(String profileId)
	{
		this.profileId = profileId;
	}

	public String getPhoneCode()
	{
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode)
	{
		this.phoneCode = phoneCode;
	}
}
