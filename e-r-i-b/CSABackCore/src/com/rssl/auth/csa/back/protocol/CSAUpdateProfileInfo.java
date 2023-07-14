package com.rssl.auth.csa.back.protocol;

import com.rssl.auth.csa.back.CSAUserInfo;

import java.util.List;

/**
 * @author osminin
 * @ created 05.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Информация об обновляемом профиле
 */
public class CSAUpdateProfileInfo
{
	private CSAUserInfo newUserInfo;
	private List<CSAUserInfo> oldUserInfoList;

	/**
	 * ctor
	 * @param newUserInfo новая информация о пользователе
	 * @param oldUserInfoList старая информация о пользователе
	 */
	public CSAUpdateProfileInfo(CSAUserInfo newUserInfo, List<CSAUserInfo> oldUserInfoList)
	{
		this.newUserInfo = newUserInfo;
		this.oldUserInfoList = oldUserInfoList;
	}

	/**
	 * @return newUserInfo новая информация о пользователе
	 */
	public CSAUserInfo getNewUserInfo()
	{
		return newUserInfo;
	}

	/**
	 * @param newUserInfo newUserInfo новая информация о пользователе
	 */
	public void setNewUserInfo(CSAUserInfo newUserInfo)
	{
		this.newUserInfo = newUserInfo;
	}

	/**
	 * @return старая информация о пользователе
	 */
	public List<CSAUserInfo> getOldUserInfoList()
	{
		return oldUserInfoList;
	}

	/**
	 * @param oldUserInfoList старая информация о пользователе
	 */
	public void setOldUserInfoList(List<CSAUserInfo> oldUserInfoList)
	{
		this.oldUserInfoList = oldUserInfoList;
	}
}
