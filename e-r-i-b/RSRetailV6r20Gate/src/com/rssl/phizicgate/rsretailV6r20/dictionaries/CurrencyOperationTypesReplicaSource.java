package com.rssl.phizicgate.rsretailV6r20.dictionaries;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Egorova
 * @ created 10.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyOperationTypesReplicaSource extends NamedQueryReplicaSource
{
	public CurrencyOperationTypesReplicaSource()
	{
		super("com.rssl.phizicgate.rsretailV6r20.dictionaries.GetCurrencyOperationTypes");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
