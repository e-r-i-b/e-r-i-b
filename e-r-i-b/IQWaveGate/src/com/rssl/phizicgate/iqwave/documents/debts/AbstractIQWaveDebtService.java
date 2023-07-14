package com.rssl.phizicgate.iqwave.documents.debts;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.impl.AbstractService;

/**
 * @author krenev
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractIQWaveDebtService extends AbstractService implements DebtService
{
	public AbstractIQWaveDebtService(GateFactory factory)
	{
		super(factory);
	}
}
