package com.rssl.phizicgate.manager.services.routable.deposit;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.deposit.DepositProductService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;
import org.w3c.dom.Document;

/**
 * @author hudyakov
 * @ created 11.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class DepositProductServiceImpl extends RoutableServiceBase implements DepositProductService
{
	public DepositProductServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Document getDepositsInfo(Office office) throws GateException, GateLogicException
	{
		DepositProductService delegate = getDelegateFactory(office).service(DepositProductService.class);
		return delegate.getDepositsInfo(office);
	}

	public Document getDepositProduct(Document params, Office office) throws GateException, GateLogicException
	{
		DepositProductService delegate = getDelegateFactory(office).service(DepositProductService.class);
		return delegate.getDepositProduct(params, office);
	}
}
