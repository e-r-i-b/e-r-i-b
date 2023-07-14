package com.rssl.phizic.business.dictionaries.mobileoperators;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.business.mobileOperators.MobileOperator;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Dorzhinov
 * @ created 05.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class MobileOperatorReplicaDestinations extends QueryReplicaDestinationBase<MobileOperator>
{
	public MobileOperatorReplicaDestinations()
	{
		super("com.rssl.phizic.business.mobileoperators.MobileOperator.getAll");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public void update(MobileOperator oldValue, MobileOperator newValue) throws GateException
	{
		if (oldValue != null && newValue != null)
			newValue.setId(oldValue.getId());
		super.update(oldValue, newValue);
	}
}
