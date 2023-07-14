package com.rssl.phizic.business.profile.images;

import java.util.Calendar;

/**
 * ���������� �� ������� � ��� �����.
 *
 * @author bogdanov
 * @ created 23.10.14
 * @ $Author$
 * @ $Revision$
 */

public class RemoteAvatarInfo
{
	private String phone;
	private String avatarPath;
	private Calendar lastUpdateTime;
	private long nodeId;

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
	 * @return ����� ���������� ����������.
	 */
	public Calendar getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime ����� ���������� ����������.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return ������������� �����.
	 */
	public long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @param nodeId ������������� �����.
	 */
	public void setNodeId(long nodeId)
	{
		this.nodeId = nodeId;
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
}
