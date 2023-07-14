package com.rssl.phizic.wsgate.types;

import com.rssl.phizic.gate.clients.CancelationCallBack;

/**
 * @author Omeliyanchuk
 * @ created 31.07.2009
 * @ $Author$
 * @ $Revision$
 */

public class CancelationCallBackType implements CancelationCallBack 
{
	String id;

	public CancelationCallBackType()
	{

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
