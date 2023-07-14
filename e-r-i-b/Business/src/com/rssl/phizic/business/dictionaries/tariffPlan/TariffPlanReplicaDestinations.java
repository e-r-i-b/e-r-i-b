package com.rssl.phizic.business.dictionaries.tariffPlan;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author EgorovaA
 * @ created 21.08.14
 * @ $Author$
 * @ $Revision$
 */
public class TariffPlanReplicaDestinations extends QueryReplicaDestinationBase
{
	public TariffPlanReplicaDestinations()
	{
		super("com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig.getAll");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
