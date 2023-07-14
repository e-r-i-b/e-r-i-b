package com.rssl.phizicgate.mock.payments.systems.gorod;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.systems.gorod.GorodService;
import com.rssl.phizic.gate.payments.systems.gorod.GorodCard;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * @author hudyakov
 * @ created 01.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class MockGorodService extends AbstractService implements GorodService
{
	public MockGorodService(GateFactory factory)
	{
		super(factory);
	}

	public GorodCard getCard(String cardId, Office office) throws GateException, GateLogicException
	{
		return null;
	}
}
