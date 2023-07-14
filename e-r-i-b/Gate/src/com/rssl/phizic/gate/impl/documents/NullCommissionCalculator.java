package com.rssl.phizic.gate.impl.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;

import java.util.Map;

/**
 * @author Krenev
 * @ created 27.11.2007
 * @ $Author$
 * @ $Revision$
 */
public class NullCommissionCalculator extends AbstractService implements CommissionCalculator
{
	public NullCommissionCalculator()
	{
		super(null);
	}

	public NullCommissionCalculator(GateFactory factory)
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params)
	{
		//nothing to do;
	}

	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		//nothing to do;
	}
}
