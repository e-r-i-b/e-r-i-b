package com.rssl.phizic.esb.ejb.federal.documents;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.XferAddRs;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 03.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * обработчик оффлайн ответа на запрос xfer
 */

public class XferAddProcessor extends ProcessorBase<XferAddRs>
{
	@Override
	protected String getExternalId(XferAddRs message, Message source)
	{
		return ExternalIdGenerator.generateExternalId(message);
	}

	@Override
	protected StatusType getStatus(XferAddRs message)
	{
		return message.getStatus();
	}

	@Override
	protected String getType(XferAddRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(XferAddRs message)
	{
		return message.getRqUID();
	}
}
