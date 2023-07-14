package com.rssl.auth.csa.utils;

import com.rssl.phizic.gate.csa.UserInfo;

import java.util.List;

/**
 * @author osminin
 * @ created 05.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �� ����������� �������
 */
public class UpdateProfileInfo
{
	private UserInfo newUserInfo;
	private List<UserInfo> oldUserInfoList;

	/**
	 * ctor
	 * @param newUserInfo ����� ���������� � ������������
	 * @param oldUserInfoList ������ ���������� � ������������
	 */
	public UpdateProfileInfo(UserInfo newUserInfo, List<UserInfo> oldUserInfoList)
	{
		this.newUserInfo = newUserInfo;
		this.oldUserInfoList = oldUserInfoList;
	}

	/**
	 * @return newUserInfo ����� ���������� � ������������
	 */
	public UserInfo getNewUserInfo()
	{
		return newUserInfo;
	}

	/**
	 * @param newUserInfo newUserInfo ����� ���������� � ������������
	 */
	public void setNewUserInfo(UserInfo newUserInfo)
	{
		this.newUserInfo = newUserInfo;
	}

	/**
	 * @return ������ ���������� � ������������
	 */
	public List<UserInfo> getOldUserInfoList()
	{
		return oldUserInfoList;
	}

	/**
	 * @param oldUserInfoList ������ ���������� � ������������
	 */
	public void setOldUserInfoList(List<UserInfo> oldUserInfoList)
	{
		this.oldUserInfoList = oldUserInfoList;
	}
}
