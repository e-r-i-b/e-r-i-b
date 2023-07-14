package com.rssl.phizic.business.profile.images;

/**
 * Связка аватара и номера телефона.
 *
 * @author bogdanov
 * @ created 23.10.14
 * @ $Author$
 * @ $Revision$
 */

public class AvatarLink
{
	private String phone;
	private String avatarPath;
	private long loginId;

	/**
	 * @return Путь к аватару.
	 */
	public String getAvatarPath()
	{
		return avatarPath;
	}

	/**
	 * @param avatarPath путь к аватару.
	 */
	public void setAvatarPath(String avatarPath)
	{
		this.avatarPath = avatarPath;
	}

	/**
	 * @return номер телефона.
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone номер телефона.
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return логин ид.
	 */
	public long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId логин ид.
	 */
	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
	}
}
