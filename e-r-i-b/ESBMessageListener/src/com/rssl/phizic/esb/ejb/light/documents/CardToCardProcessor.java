package com.rssl.phizic.esb.ejb.light.documents;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.CardToCardResponse;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.HeadResponseType;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 18.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * обработчик оффлайн ответа на запрос перевода между картами
 */

public class CardToCardProcessor extends ProcessorBase<CardToCardResponse>
{
	@Override
	protected String getExternalId(CardToCardResponse message, Message source)
	{
		return ExternalIdGenerator.generateExternalId(message);
	}

	@Override
	protected HeadResponseType.Error getStatus(CardToCardResponse message)
	{
		return message.getHead().getError();
	}

	@Override
	protected String getType(CardToCardResponse message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(CardToCardResponse message)
	{
		return message.getHead().getMessUID().getMessageId();
	}
}
