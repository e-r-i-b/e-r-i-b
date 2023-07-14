package com.rssl.phizic.web.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author osminin
 * @ created 13.11.2008
 * @ $Author$
 * @ $Revision$
 */

public class ListLimitActionForm<T> extends ListFormBase<T>
{
	private int listLimit;

	public void setListLimit(int listLimit)
	{
		this.listLimit = listLimit;
	}

	public int getListLimit()
	{
		return listLimit;
	}
}
