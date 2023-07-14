package com.rssl.phizic.esb.ejb.light.documents;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.HeadResponseType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.SimplePaymentResponse;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 18.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * обработчик оффлайн ответа на запрос типовой оплаты
 */

public class SimplePaymentProcessor extends ProcessorBase<SimplePaymentResponse>
{
	@Override
	protected String getExternalId(SimplePaymentResponse message, Message source)
	{
		return ExternalIdGenerator.generateExternalId(message);
	}

	@Override
	protected HeadResponseType.Error getStatus(SimplePaymentResponse message)
	{
		return message.getHead().getError();
	}

	@Override
	protected String getType(SimplePaymentResponse message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(SimplePaymentResponse message)
	{
		return message.getHead().getMessUID().getMessageId();
	}
}
