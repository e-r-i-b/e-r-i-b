package com.rssl.phizicgate.ips;

import com.rssl.phizgate.common.routable.ClientBase;
import com.rssl.phizic.gate.clients.Client;

/**
 * @author Erkin
 * @ created 01.08.2011
 * @ $Author$
 * @ $Revision$
 */
class TestClient extends ClientBase implements Client
{
	private String displayId;

	private boolean resident;

	////////////////////////////////////////////////////////////////

	public String getDisplayId()
	{
		return displayId;
	}

	void setDisplayId(String displayId)
	{
		this.displayId = displayId;
	}

	public boolean isResident()
	{
		return resident;
	}

	void setResident(boolean resident)
	{
		this.resident = resident;
	}
}
