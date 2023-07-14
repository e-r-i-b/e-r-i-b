package com.rssl.phizic.business.profile;

import java.util.Calendar;

/**
 * ������� ���������.
 *
 * @author bogdanov
 * @ created 08.10.14
 * @ $Author$
 * @ $Revision$
 */

public class IncognitoPhone
{
	private String phone;
	private Calendar lastUpdateTime;
	private boolean active;
	private long nodeId;

	/**
	 * @return �������� ������ ��� ���������.
	 */
	public boolean isActive()
	{
		return active;
	}

	/**
	 * @param active �������� ������.
	 */
	public void setActive(boolean active)
	{
		this.active = active;
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
	 * @return ����� �����.
	 */
	public long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @param nodeId ����� �����.
	 */
	public void setNodeId(long nodeId)
	{
		this.nodeId = nodeId;
	}
}
