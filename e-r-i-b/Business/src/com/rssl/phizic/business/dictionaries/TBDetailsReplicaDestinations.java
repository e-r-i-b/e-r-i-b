package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Цель репликации справочника ТБ
 * @author Pankin
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 */
public class TBDetailsReplicaDestinations extends QueryReplicaDestinationBase
{
	protected TBDetailsReplicaDestinations()
	{
		super("com.rssl.phizic.business.departments.TerBankDetails.getAll");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
