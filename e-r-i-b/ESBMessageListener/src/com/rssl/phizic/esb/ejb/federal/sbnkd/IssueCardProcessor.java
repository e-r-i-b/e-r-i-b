package com.rssl.phizic.esb.ejb.federal.sbnkd;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.IssueCardRs;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик заявки на выпуск карты.
 */

public class IssueCardProcessor extends ProcessorBase<IssueCardRs>
{
	@Override
	protected String getExternalId(IssueCardRs message, Message source)
	{
		return message.getRqUID();
	}

	@Override
	protected Object getStatus(IssueCardRs message)
	{
		return message.getStatus();
	}

	@Override
	protected String getType(IssueCardRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(IssueCardRs message)
	{
		return message.getRqUID();
	}
}
