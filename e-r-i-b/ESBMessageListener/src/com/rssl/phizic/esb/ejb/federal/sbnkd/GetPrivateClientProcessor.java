package com.rssl.phizic.esb.ejb.federal.sbnkd;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateClientRs;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик заявки на получение информации по клиенту ФЛ
 */

public class GetPrivateClientProcessor extends ProcessorBase<GetPrivateClientRs>
{
	@Override
	protected String getExternalId(GetPrivateClientRs message, Message source)
	{
		return message.getRqUID();
	}

	@Override
	protected Object getStatus(GetPrivateClientRs message)
	{
		return message.getStatus();
	}

	@Override
	protected String getType(GetPrivateClientRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(GetPrivateClientRs message)
	{
		return message.getRqUID();
	}
}
