package com.rssl.phizic.esb.ejb.federal.documents;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.AcceptBillBasketExecuteRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;

import javax.jms.Message;

/**
 * обработчик оффлайн ответа на запрос акцепта инвойса корзины
 * @author Niculichev
 * @ created 25.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AcceptBillBasketExecuteProcessor extends ProcessorBase<AcceptBillBasketExecuteRs>
{
	@Override
	protected String getExternalId(AcceptBillBasketExecuteRs message, Message source)
	{
		return ExternalIdGenerator.generateExternalId(message);
	}

	@Override
	protected StatusType getStatus(AcceptBillBasketExecuteRs message)
	{
		return message.getStatus();
	}

	@Override
	protected String getType(AcceptBillBasketExecuteRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(AcceptBillBasketExecuteRs message)
	{
		return message.getRqUID();
	}
}
