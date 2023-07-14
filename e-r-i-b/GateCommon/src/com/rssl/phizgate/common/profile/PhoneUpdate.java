package com.rssl.phizgate.common.profile;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Запись об обновлении телефонов.
 *
 * @author bogdanov
 * @ created 29.01.15
 * @ $Author$
 * @ $Revision$
 */

public class PhoneUpdate implements Serializable
{
	private Calendar updateDate;
	private Long updateIndex;
	private boolean newItem;

	public boolean isNewItem()
	{
		return newItem;
	}

	public void setNewItem(boolean newItem)
	{
		this.newItem = newItem;
	}

	public Calendar getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Calendar updateDate)
	{
		this.updateDate = updateDate;
	}

	public Long getUpdateIndex()
	{
		return updateIndex;
	}

	public void setUpdateIndex(Long updateIndex)
	{
		this.updateIndex = updateIndex;
	}
}
