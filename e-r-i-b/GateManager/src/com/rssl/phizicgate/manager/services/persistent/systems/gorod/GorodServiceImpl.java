package com.rssl.phizicgate.manager.services.persistent.systems.gorod;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.gorod.GorodCard;
import com.rssl.phizic.gate.payments.systems.gorod.GorodService;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

/**
 * @author Gainanov
 * @ created 19.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class GorodServiceImpl extends PersistentServiceBase<GorodService> implements GorodService
{
	public GorodServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public GorodCard getCard(String cardId, Office office) throws GateException, GateLogicException
	{
		return delegate.getCard(cardId, removeRouteInfo(office));
	}
}
