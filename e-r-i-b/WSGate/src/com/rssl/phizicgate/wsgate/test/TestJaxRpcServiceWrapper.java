package com.rssl.phizicgate.wsgate.test;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.test.TestJaxRpcService;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.test.generated.TestJaxRpcService_Stub;

/**
 * Wrapper для клиентской части тестового веб-сервиса
 * @author Puzikov
 * @ created 11.09.13
 * @ $Author$
 * @ $Revision$
 */

public class TestJaxRpcServiceWrapper extends JAXRPCClientSideServiceBase<TestJaxRpcService_Stub> implements TestJaxRpcService
{
	protected TestJaxRpcServiceWrapper(GateFactory factory)
	{
		super(factory);
	}

	public Client testCardToClient(Card card)
	{
		return null;
	}
}
