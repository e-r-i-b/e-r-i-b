package com.rssl.phizic.esb.ejb.federal.documents;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SetAccountStateRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * обработчик оффлайн ответа на запрос об утере сберегательной книжки
 */

public class SetAccountStateProcessor extends ProcessorBase<SetAccountStateRs>
{
	@Override
	protected String getExternalId(SetAccountStateRs message, Message source)
	{
		return ExternalIdGenerator.generateExternalId(message);
	}

	@Override
	protected StatusType getStatus(SetAccountStateRs message)
	{
		return message.getStatus();
	}

	@Override
	protected String getType(SetAccountStateRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(SetAccountStateRs message)
	{
		return message.getRqUID();
	}
}
