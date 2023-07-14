package com.rssl.phizic.esb.ejb.federal.documents;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SvcAcctDelRs;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 05.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * обработчик оффлайн ответа на запрос SvcAcctDel
 */

public class SvcAcctDelProcessor extends ProcessorBase<SvcAcctDelRs>
{
	@Override
	protected String getExternalId(SvcAcctDelRs message, Message source)
	{
		return ExternalIdGenerator.generateExternalId(message);
	}

	@Override
	protected StatusType getStatus(SvcAcctDelRs message)
	{
		return message.getStatus();
	}

	@Override
	protected String getType(SvcAcctDelRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(SvcAcctDelRs message)
	{
		return message.getRqUID();
	}
}
