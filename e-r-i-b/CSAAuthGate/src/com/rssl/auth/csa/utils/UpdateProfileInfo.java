package com.rssl.auth.csa.utils;

import com.rssl.phizic.gate.csa.UserInfo;

import java.util.List;

/**
 * @author osminin
 * @ created 05.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Информация об обновляемом профиле
 */
public class UpdateProfileInfo
{
	private UserInfo newUserInfo;
	private List<UserInfo> oldUserInfoList;

	/**
	 * ctor
	 * @param newUserInfo новая информация о пользователе
	 * @param oldUserInfoList старая информация о пользователе
	 */
	public UpdateProfileInfo(UserInfo newUserInfo, List<UserInfo> oldUserInfoList)
	{
		this.newUserInfo = newUserInfo;
		this.oldUserInfoList = oldUserInfoList;
	}

	/**
	 * @return newUserInfo новая информация о пользователе
	 */
	public UserInfo getNewUserInfo()
	{
		return newUserInfo;
	}

	/**
	 * @param newUserInfo newUserInfo новая информация о пользователе
	 */
	public void setNewUserInfo(UserInfo newUserInfo)
	{
		this.newUserInfo = newUserInfo;
	}

	/**
	 * @return старая информация о пользователе
	 */
	public List<UserInfo> getOldUserInfoList()
	{
		return oldUserInfoList;
	}

	/**
	 * @param oldUserInfoList старая информация о пользователе
	 */
	public void setOldUserInfoList(List<UserInfo> oldUserInfoList)
	{
		this.oldUserInfoList = oldUserInfoList;
	}
}
