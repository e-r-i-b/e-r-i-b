package com.rssl.phizic.web.ermb;

import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * User: Moshenko
 * Date: 18.06.2013
 * Time: 16:34:59
 * Управление телефонами ЕРМБ
 */
public class ErmbControlPhoneForm extends ActionFormBase
{
	private String confirmCode;
	private String phoneNumber;
	private String[] phoneCode;
	private Long profileId;
	private String[] phonesToDelete;

	public String getConfirmCode()
	{
		return confirmCode;
	}

	public void setConfirmCode(String confirmCode)
	{
		this.confirmCode = confirmCode;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String[] getPhoneCode()
	{
		return phoneCode;
	}

	public void setPhoneCode(String[] phoneCode)
	{
		this.phoneCode = phoneCode;
	}

	public Long getProfileId()
	{
		return profileId;
	}

	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	public String[] getPhonesToDelete()
	{
		return phonesToDelete;
	}

	public void setPhonesToDelete(String[] phonesToDelete)
	{
		this.phonesToDelete = phonesToDelete;
	}
}
