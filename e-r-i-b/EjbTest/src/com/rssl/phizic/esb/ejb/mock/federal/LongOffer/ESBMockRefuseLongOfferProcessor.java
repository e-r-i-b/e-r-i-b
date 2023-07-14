package com.rssl.phizic.esb.ejb.mock.federal.LongOffer;

import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.federal.FederalESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SvcAcctDelRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SvcAcctDelRs;

/**
 * @author akrenev
 * @ created 05.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * ��������, ����������� ������ ���� ��� ������� ������ ��
 */

public final class ESBMockRefuseLongOfferProcessor extends FederalESBMockProcessorBase<SvcAcctDelRq, SvcAcctDelRs>
{
	/**
	 * �����������
	 * @param module ������
	 */
	public ESBMockRefuseLongOfferProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected boolean needSendResult(ESBMessage<SvcAcctDelRq> xmlRequest, SvcAcctDelRs message)
	{
		return getRandomBoolean();
	}

	@Override
	protected boolean needSendOnline(ESBMessage<SvcAcctDelRq> xmlRequest, SvcAcctDelRs message)
	{
		return getRandomBoolean();
	}

	@Override
	protected void process(ESBMessage<SvcAcctDelRq> xmlRequest)
	{
		SvcAcctDelRq request = xmlRequest.getObject();
		SvcAcctDelRs response = new SvcAcctDelRs();

		response.setRqUID(request.getRqUID());
		response.setRqTm(request.getRqTm());
		response.setOperUID(request.getOperUID());
		response.setStatus(getOkStatus());

		send(xmlRequest, response, "RefuseLongOffer");
	}
}
