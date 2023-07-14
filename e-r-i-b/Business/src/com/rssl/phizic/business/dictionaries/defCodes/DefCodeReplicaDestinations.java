package com.rssl.phizic.business.dictionaries.defCodes;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Цель репликации справочника Def-кодов
 * @author Rtischeva
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */
public class DefCodeReplicaDestinations extends QueryReplicaDestinationBase<DefCode>
{
	public DefCodeReplicaDestinations()
	{
		super("com.rssl.phizic.business.dictionaries.defCodes.DefCode.getAll");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
