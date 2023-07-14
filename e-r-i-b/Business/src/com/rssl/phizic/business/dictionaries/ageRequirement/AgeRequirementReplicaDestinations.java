package com.rssl.phizic.business.dictionaries.ageRequirement;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author EgorovaA
 * @ created 21.08.14
 * @ $Author$
 * @ $Revision$
 */
public class AgeRequirementReplicaDestinations extends QueryReplicaDestinationBase
{
	public AgeRequirementReplicaDestinations()
	{
		super("com.rssl.phizic.business.dictionaries.ageRequirement.AgeRequirement.getAll");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
