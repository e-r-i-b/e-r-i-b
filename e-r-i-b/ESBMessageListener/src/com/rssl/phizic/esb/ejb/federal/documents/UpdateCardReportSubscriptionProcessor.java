package com.rssl.phizic.esb.ejb.federal.documents;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.UpdateCardReportSubscriptionRs;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * обработчик оффлайн ответа на запрос управления подпиской по карте
 */

public class UpdateCardReportSubscriptionProcessor extends ProcessorBase<UpdateCardReportSubscriptionRs>
{
	@Override
	protected String getExternalId(UpdateCardReportSubscriptionRs message, Message source)
	{
		try
		{
			return source.getJMSCorrelationID();
		}
		catch (JMSException e)
		{
			throw new InternalErrorException("Ошибка получения идентификатора сообщения.", e);
		}
	}

	@Override
	protected StatusType getStatus(UpdateCardReportSubscriptionRs message)
	{
		return message.getStatus();
	}

	@Override
	protected String getType(UpdateCardReportSubscriptionRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(UpdateCardReportSubscriptionRs message)
	{
		return message.getRqUID();
	}
}
