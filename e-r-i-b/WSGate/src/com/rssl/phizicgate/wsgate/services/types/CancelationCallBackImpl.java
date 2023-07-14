package com.rssl.phizicgate.wsgate.services.types;

import com.rssl.phizic.gate.clients.CancelationCallBack;

/**
 * @author Omeliyanchuk
 * @ created 03.08.2009
 * @ $Author$
 * @ $Revision$
 */

public class CancelationCallBackImpl implements CancelationCallBack
{
	String id;
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
