package com.rssl.phizicgate.manager.services.routable.payments.gorod;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.gorod.GorodCard;
import com.rssl.phizic.gate.payments.systems.gorod.GorodService;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;

/**
 * @author hudyakov
 * @ created 11.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class GorodServiceImpl extends RoutableServiceBase implements GorodService
{
	public GorodServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public GorodCard getCard(String cardId, Office office) throws GateException, GateLogicException
	{
		GorodService delegate = getDelegateFactory(office).service(GorodService.class);
		return delegate.getCard(cardId, office);
	}
}
