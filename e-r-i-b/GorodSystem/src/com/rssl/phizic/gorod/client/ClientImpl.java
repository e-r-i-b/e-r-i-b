package com.rssl.phizic.gorod.client;

import com.rssl.phizgate.common.routable.ClientBase;

/**
 * @author Egorova
 * @ created 25.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class ClientImpl extends ClientBase
{
	private boolean isResident;

	public String getDisplayId()
	{
		return getId();
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
