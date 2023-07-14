package com.rssl.auth.csa.back.protocol;

import com.rssl.auth.csa.back.CSAUserInfo;

import java.util.List;

/**
 * @author osminin
 * @ created 05.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �� ����������� �������
 */
public class CSAUpdateProfileInfo
{
	private CSAUserInfo newUserInfo;
	private List<CSAUserInfo> oldUserInfoList;

	/**
	 * ctor
	 * @param newUserInfo ����� ���������� � ������������
	 * @param oldUserInfoList ������ ���������� � ������������
	 */
	public CSAUpdateProfileInfo(CSAUserInfo newUserInfo, List<CSAUserInfo> oldUserInfoList)
	{
		this.newUserInfo = newUserInfo;
		this.oldUserInfoList = oldUserInfoList;
	}

	/**
	 * @return newUserInfo ����� ���������� � ������������
	 */
	public CSAUserInfo getNewUserInfo()
	{
		return newUserInfo;
	}

	/**
	 * @param newUserInfo newUserInfo ����� ���������� � ������������
	 */
	public void setNewUserInfo(CSAUserInfo newUserInfo)
	{
		this.newUserInfo = newUserInfo;
	}

	/**
	 * @return ������ ���������� � ������������
	 */
	public List<CSAUserInfo> getOldUserInfoList()
	{
		return oldUserInfoList;
	}

	/**
	 * @param oldUserInfoList ������ ���������� � ������������
	 */
	public void setOldUserInfoList(List<CSAUserInfo> oldUserInfoList)
	{
		this.oldUserInfoList = oldUserInfoList;
	}
}
