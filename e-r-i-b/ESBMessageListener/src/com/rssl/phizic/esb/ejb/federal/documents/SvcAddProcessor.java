package com.rssl.phizic.esb.ejb.federal.documents;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SvcAddRs;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 08.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * обработчик оффлайн ответа на запрос SvcAdd
 */

public class SvcAddProcessor extends ProcessorBase<SvcAddRs>
{
	@Override
	protected String getExternalId(SvcAddRs message, Message source)
	{
		return ExternalIdGenerator.generateExternalId(message);
	}

	@Override
	protected StatusType getStatus(SvcAddRs message)
	{
		return message.getStatus();
	}

	@Override
	protected String getType(SvcAddRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(SvcAddRs message)
	{
		return message.getRqUID();
	}
}
