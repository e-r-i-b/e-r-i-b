package com.rssl.phizic.esb.ejb.federal.sbnkd;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.ConcludeEDBORs;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик заявки на подключение ФЛ к договору УДБО
 */

public class ConcludeEDBOProcessor extends ProcessorBase<ConcludeEDBORs>
{
	@Override
	protected String getExternalId(ConcludeEDBORs message, Message source)
	{
		return message.getRqUID();
	}

	@Override
	protected Object getStatus(ConcludeEDBORs message)
	{
		return message.getStatus();
	}

	@Override
	protected String getType(ConcludeEDBORs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(ConcludeEDBORs message)
	{
		return message.getRqUID();
	}
}
