package com.rssl.phizicgate.esberibgate;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.ws.TransportProvider;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;

/**
 * @author egorova
 * @ created 08.06.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class AbstractService extends com.rssl.phizic.gate.impl.AbstractService
{
	protected AbstractService(GateFactory factory)
	{
		super(factory);
	}

	protected IFXRs_Type getRequest(IFXRq_Type ifxRq) throws GateException, GateLogicException
	{
		return TransportProvider.doRequest(ifxRq);
	}

	public <T>T[] toEntity(Object[] objects, T[] entityes)
	{
		int length = objects.length > entityes.length ? entityes.length: objects.length;
		for(int i = 0; i< length; i++)
		{
			entityes[i] = (T)objects[i];
		}
		return entityes;
	}
}