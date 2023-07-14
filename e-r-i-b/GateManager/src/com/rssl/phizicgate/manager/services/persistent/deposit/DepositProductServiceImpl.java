package com.rssl.phizicgate.manager.services.persistent.deposit;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.deposit.DepositProductService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;
import org.w3c.dom.Document;

/**
 * @author Krenev
 * @ created 30.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductServiceImpl extends PersistentServiceBase<DepositProductService> implements DepositProductService
{
	public DepositProductServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Document getDepositsInfo(Office office) throws GateException, GateLogicException
	{
		return delegate.getDepositsInfo(removeRouteInfo(office));
	}

	public Document getDepositProduct(Document params, Office office) throws GateException, GateLogicException
	{
		return delegate.getDepositProduct(params, removeRouteInfo(office));
	}
}
