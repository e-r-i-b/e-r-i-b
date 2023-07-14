package com.rssl.phizic.business.profile;

import java.util.Calendar;

/**
 * “елефон инкогнито.
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
	 * @return активна€ запись или удаленна€.
	 */
	public boolean isActive()
	{
		return active;
	}

	/**
	 * @param active активна€ запись.
	 */
	public void setActive(boolean active)
	{
		this.active = active;
	}

	/**
	 * @return врем€ последнего обновлени€.
	 */
	public Calendar getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime врем€ последнего обновлени€.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
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
	 * @return номер блкоа.
	 */
	public long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @param nodeId номер блока.
	 */
	public void setNodeId(long nodeId)
	{
		this.nodeId = nodeId;
	}
}
