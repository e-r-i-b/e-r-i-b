package com.rssl.phizic.esb.ejb.federal.documents;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.CardBlockRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * обработчик оффлайн ответа на запрос блокировки карты
 */

public class CardBlockProcessor extends ProcessorBase<CardBlockRs>
{
	@Override
	protected String getExternalId(CardBlockRs message, Message source)
	{
		return ExternalIdGenerator.generateExternalId(message);
	}

	@Override
	protected StatusType getStatus(CardBlockRs message)
	{
		return message.getStatus();
	}

	@Override
	protected String getType(CardBlockRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(CardBlockRs message)
	{
		return message.getRqUID();
	}
}
