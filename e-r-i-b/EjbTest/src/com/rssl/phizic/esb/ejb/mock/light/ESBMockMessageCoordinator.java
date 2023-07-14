package com.rssl.phizic.esb.ejb.mock.light;

import com.rssl.phizic.TestModule;
import com.rssl.phizic.esb.ejb.mock.light.CardToCard.ESBMockCardToCardCommissionProcessor;
import com.rssl.phizic.esb.ejb.mock.light.CardToCard.ESBMockCardToCardPaymentProcessor;
import com.rssl.phizic.esb.ejb.mock.light.PaymentSystemPayment.ESBMockPaymentSystemPaymentProcessor;
import com.rssl.phizic.messaging.MessageCoordinator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.CardToCardInfoRequest;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.CardToCardRequest;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.SimplePaymentRequest;

/**
 * @author akrenev
 * @ created 18.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Маршрутизатор сообщений в сегмент "легкой" шины
 */

public class ESBMockMessageCoordinator extends MessageCoordinator
{
	ESBMockMessageCoordinator()
	{
		TestModule testModule = moduleManager.getModule(TestModule.class);

		registerProcessor(CardToCardRequest.class,     new ESBMockCardToCardPaymentProcessor(testModule));
		registerProcessor(CardToCardInfoRequest.class, new ESBMockCardToCardCommissionProcessor(testModule));
		registerProcessor(SimplePaymentRequest.class,  new ESBMockPaymentSystemPaymentProcessor(testModule));
	}
}
