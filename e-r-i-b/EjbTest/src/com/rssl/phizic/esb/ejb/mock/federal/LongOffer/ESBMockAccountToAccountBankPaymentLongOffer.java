package com.rssl.phizic.esb.ejb.mock.federal.LongOffer;

import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.federal.FederalESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizicgate.esberibgate.documents.senders.XferMethodType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SvcAcctId;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SvcAddRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SvcAddRs;

/**
 * @author akrenev
 * @ created 08.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * заглушка, имитирующа€ работу шины при запросе создани€ ƒѕ физ.лицу со вклада
 */

public final class ESBMockAccountToAccountBankPaymentLongOffer extends FederalESBMockProcessorBase<SvcAddRq, SvcAddRs>
{
	/**
	 * конструктор
	 * @param module модуль
	 */
	public ESBMockAccountToAccountBankPaymentLongOffer(Module module)
	{
		super(module);
	}

	@Override
	protected boolean needSendResult(ESBMessage<SvcAddRq> xmlRequest, SvcAddRs message)
	{
		return getRandomBoolean();
	}

	@Override
	protected boolean needSendOnline(ESBMessage<SvcAddRq> xmlRequest, SvcAddRs message)
	{
		return getRandomBoolean();
	}

	@Override
	protected void process(ESBMessage<SvcAddRq> xmlRequest)
	{
		SvcAddRq request = xmlRequest.getObject();
		SvcAddRs response = new SvcAddRs();

		String serviceName = XferMethodType.EXTERNAL_BANK.name().equals(request.getXferInfo().getXferMethod())? "AccountToAccountIntraBankPaymentLongOffer":  "AccountToAccountOurBankPaymentLongOffer";

		response.setRqUID(request.getRqUID());
		response.setRqTm(request.getRqTm());
		response.setOperUID(request.getOperUID());

		SvcAcctId svcAcctId = new SvcAcctId();
		svcAcctId.setSvcAcctNum(getRandomLong());
		svcAcctId.setBankInfo(request.getBankInfo());

		response.setSvcAcctId(svcAcctId);
		response.setStatus(getRandomBoolean()? getOkStatus(): getErrorStatus());

		send(xmlRequest, response, serviceName);
	}
}
