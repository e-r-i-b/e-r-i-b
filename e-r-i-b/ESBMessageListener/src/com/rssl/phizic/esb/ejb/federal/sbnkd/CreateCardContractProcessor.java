package com.rssl.phizic.esb.ejb.federal.sbnkd;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.CreateCardContractRs;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик заявки на создание договора на карту
 */

public class CreateCardContractProcessor extends ProcessorBase<CreateCardContractRs>
{
	@Override
	protected String getExternalId(CreateCardContractRs message, Message source)
	{
		return message.getRqUID();
	}

	@Override
	protected Object getStatus(CreateCardContractRs message)
	{
		return message.getStatus();
	}

	@Override
	protected String getType(CreateCardContractRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(CreateCardContractRs message)
	{
		return message.getRqUID();
	}
}
