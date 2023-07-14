package com.rssl.phizicgate.sbrf.client;

import com.rssl.phizic.gate.clients.CancelationCallBack;

/**
 * @author Omeliyanchuk
 * @ created 30.07.2009
 * @ $Author$
 * @ $Revision$
 */

public class CancelationCallBackImpl implements CancelationCallBack
{
	String id;

	public CancelationCallBackImpl()
	{

	}

	public CancelationCallBackImpl(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
