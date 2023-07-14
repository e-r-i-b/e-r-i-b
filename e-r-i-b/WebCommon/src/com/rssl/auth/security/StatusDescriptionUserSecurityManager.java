package com.rssl.auth.security;

import java.util.Calendar;

/**
 * ����� ����������� ����� ����� ������������. ����������� ��� ������ SessionSecurityManagerImp
 * @author usachev
 * @ created 17.02.15
 * @ $Author$
 * @ $Revision$
 */
public class StatusDescriptionUserSecurityManager
{
	private Calendar lastAccessTime;
	private boolean isTrustUser;
	/**
	 * ��������� ������� ���������� ����� ������������ ��� �����������
	 * @return ����� ���������� �����
	 */
	public Calendar getLastAccessTime()
	{
		return lastAccessTime;
	}

	/**
	 * ��������� ������� ���������� ����� ������������ ��� �����������
	 * @param lastAccessTime  �����
	 */
	public void setLastAccessTime(Calendar lastAccessTime)
	{
		this.lastAccessTime = lastAccessTime;
	}


	/**
	 * ���� �� ����� ���������� �����, ��� ���������� �������������
	 * @return ��, ���� ���� �����. ���, � ��������� ������.
	 */
	public boolean hasLastTimeVisit(){
		return lastAccessTime != null;
	}

	/**
	 * ��������, �������� �� �� ������������ ��� ���
	 * @return ��, ���� ��������. ��� � ��������� ������
	 */
	public boolean isTrustUser()
	{
		return isTrustUser;
	}

	/**
	 * ��������� ����� ������������ � ������������.
	 * @param trustUser ��, ���� ��������. ��� � ��������� ������.
	 */
	public void setTrustUser(boolean trustUser)
	{
		isTrustUser = trustUser;
	}
}
