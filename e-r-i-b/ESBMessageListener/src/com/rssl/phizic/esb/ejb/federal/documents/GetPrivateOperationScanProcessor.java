package com.rssl.phizic.esb.ejb.federal.documents;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateOperationScanRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * обработчик оффлайн ответа на запрос БФО
 */

public class GetPrivateOperationScanProcessor extends ProcessorBase<GetPrivateOperationScanRs>
{
	@Override
	protected String getExternalId(GetPrivateOperationScanRs message, Message source)
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
	protected StatusType getStatus(GetPrivateOperationScanRs message)
	{
		return message.getStatus();
	}

	@Override
	protected String getType(GetPrivateOperationScanRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(GetPrivateOperationScanRs message)
	{
		return message.getRqUID();
	}
}
