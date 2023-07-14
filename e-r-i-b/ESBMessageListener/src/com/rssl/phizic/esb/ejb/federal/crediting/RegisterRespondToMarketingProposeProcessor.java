package com.rssl.phizic.esb.ejb.federal.crediting;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.RegisterRespondToMarketingProposeRs;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� CRM �� �������������� ������������ (����� �� ������)
 */

public class RegisterRespondToMarketingProposeProcessor extends ProcessorBase<RegisterRespondToMarketingProposeRs>
{
	@Override
	protected String getType(RegisterRespondToMarketingProposeRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(RegisterRespondToMarketingProposeRs message)
	{
		return message.getRqUID();
	}

	@Override
	protected void process(RegisterRespondToMarketingProposeRs message, Message source)
	{
		try
		{
			getCrmOffersResponseProcessor().processFeedbackResponse(message, source.getJMSCorrelationID());
		}
		catch (Exception e)
		{
			throw new InternalErrorException("������ ��������� ���������." + getType(message) + " (" + getId(message) + ")", e);
		}
	}
}
