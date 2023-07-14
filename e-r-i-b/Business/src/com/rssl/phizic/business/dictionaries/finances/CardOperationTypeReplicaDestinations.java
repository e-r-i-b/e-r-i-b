package com.rssl.phizic.business.dictionaries.finances;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Gololobov
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class CardOperationTypeReplicaDestinations extends QueryReplicaDestinationBase
{
	public CardOperationTypeReplicaDestinations()
	{
		super("com.rssl.phizic.business.dictionaries.finances.getAllOperationTypes");
	}

	public void initialize(GateFactory factory) throws GateException
	{

	}
}
