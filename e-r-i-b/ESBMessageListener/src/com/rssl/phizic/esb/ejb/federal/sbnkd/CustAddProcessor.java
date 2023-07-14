package com.rssl.phizic.esb.ejb.federal.sbnkd;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.CustAddRs;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик заявки создания нового клиента в процессинговой системе
 */

public class CustAddProcessor extends ProcessorBase<CustAddRs>
{
	@Override
	protected String getExternalId(CustAddRs message, Message source)
	{
		return message.getRqUID();
	}

	@Override
	protected Object getStatus(CustAddRs message)
	{
		return message.getStatus();
	}

	@Override
	protected String getType(CustAddRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(CustAddRs message)
	{
		return message.getRqUID();
	}
}
