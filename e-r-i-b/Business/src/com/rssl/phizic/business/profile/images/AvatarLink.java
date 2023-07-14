package com.rssl.phizic.business.profile.images;

/**
 * ������ ������� � ������ ��������.
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
	 * @return ���� � �������.
	 */
	public String getAvatarPath()
	{
		return avatarPath;
	}

	/**
	 * @param avatarPath ���� � �������.
	 */
	public void setAvatarPath(String avatarPath)
	{
		this.avatarPath = avatarPath;
	}

	/**
	 * @return ����� ��������.
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone ����� ��������.
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return ����� ��.
	 */
	public long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId ����� ��.
	 */
	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
	}
}
