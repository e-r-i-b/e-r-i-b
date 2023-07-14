package com.rssl.phizic.esb.ejb.mock.federal.basket;

import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.federal.FederalESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.AcceptBillBasketExecuteRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.AcceptBillBasketExecuteRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;

/**
 * Процесор заглушки для обработки запроса на акцепт по инвойсу
 * @author Niculichev
 * @ created 25.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AcceptBillBasketExecuteMessageProcessor extends FederalESBMockProcessorBase<AcceptBillBasketExecuteRq, AcceptBillBasketExecuteRs>
{
	public AcceptBillBasketExecuteMessageProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected boolean needSendResult(ESBMessage<AcceptBillBasketExecuteRq> xmlRequest, AcceptBillBasketExecuteRs message)
	{
		return true;
	}

	@Override
	protected boolean needSendOnline(ESBMessage<AcceptBillBasketExecuteRq> xmlRequest, AcceptBillBasketExecuteRs message)
	{
		return true;
	}

	@Override
	protected void process(ESBMessage<AcceptBillBasketExecuteRq> xmlRequest)
	{
		AcceptBillBasketExecuteRq request = xmlRequest.getObject();
		AcceptBillBasketExecuteRs rs = new AcceptBillBasketExecuteRs();

		rs.setRqUID(request.getRqUID());
		rs.setOperUID(request.getOperUID());
		rs.setRqTm(request.getRqTm());
		rs.setSPName(request.getSPName());
		rs.setSystemId(request.getSystemId());

		StatusType status = new StatusType();
		status.setStatusCode(0L);
		rs.setStatus(status);

		send(xmlRequest, rs, "InvoiceAcceptPayment");
	}
}
