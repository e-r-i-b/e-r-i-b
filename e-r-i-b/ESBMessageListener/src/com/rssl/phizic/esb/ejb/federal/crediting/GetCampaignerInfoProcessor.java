package com.rssl.phizic.esb.ejb.federal.crediting;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetCampaignerInfoRs;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * обработчик ответов CRM по предодобренным предложениям (ответ с предложениями)
 */

public class GetCampaignerInfoProcessor extends ProcessorBase<GetCampaignerInfoRs>
{
	@Override
	protected String getType(GetCampaignerInfoRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(GetCampaignerInfoRs message)
	{
		return message.getRqUID();
	}

	@Override
	protected void process(GetCampaignerInfoRs message, Message source)
	{
		try
		{
			getCrmOffersResponseProcessor().processOfferResponse(message);
		}
		catch (Exception e)
		{
			throw new InternalErrorException("Ошибка обработки сообщения." + getType(message) + " (" + getId(message) + ")", e);
		}
	}
}
