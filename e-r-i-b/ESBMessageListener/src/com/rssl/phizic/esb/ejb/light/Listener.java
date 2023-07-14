package com.rssl.phizic.esb.ejb.light;

import com.rssl.phizic.esb.ejb.ESBMessageListenerBase;
import com.rssl.phizic.esb.ejb.MessageProcessor;
import com.rssl.phizic.esb.ejb.light.documents.*;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 18.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Листенер "легкой" шины
 */

public class Listener extends ESBMessageListenerBase
{
	private static final Map<Class, MessageProcessor> processors = new HashMap<Class, MessageProcessor>();

	static
	{
		processors.put(CardToCardResponse.class,     new CardToCardProcessor());
		processors.put(CardToCardInfoResponse.class, null);
		processors.put(SimplePaymentResponse.class,  new SimplePaymentProcessor());
	}

	@Override
	protected ESBSegment getSegment()
	{
		return ESBSegment.light;
	}

	@Override
	protected MessageProcessor getProcessor(Object response)
	{
		return processors.get(response.getClass());
	}
}
