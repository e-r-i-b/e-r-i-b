package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizgate.common.routable.ClientBase;

/**
 * @author gladishev
 * @ created 14.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class ClientImpl extends ClientBase
{
	private String displayId;
	private boolean isResident;

	public String getDisplayId()
	{
		return displayId;
	}

	public void setDisplayId(String displayId)
	{
		this.displayId = displayId;
	}

	public boolean isResident()
	{
		return isResident;
	}

	public void setResident(boolean resident)
	{
		isResident = resident;
	}
}