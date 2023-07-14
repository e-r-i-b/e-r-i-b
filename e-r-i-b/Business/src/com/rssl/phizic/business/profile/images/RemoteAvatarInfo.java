package com.rssl.phizic.business.profile.images;

import java.util.Calendar;

/**
 * Информация об аватаре в ЦСА Адмни.
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
	 * @return путь к аватару.
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
	 * @return время последнего обновления.
	 */
	public Calendar getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime время последенго обновления.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return идентификатор блока.
	 */
	public long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @param nodeId идентификатор блока.
	 */
	public void setNodeId(long nodeId)
	{
		this.nodeId = nodeId;
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
}
