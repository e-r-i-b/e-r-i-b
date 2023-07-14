package com.rssl.phizic.esb.ejb.mock.federal.p2pcomission;

import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.federal.FederalESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.CalcCardToCardTransferCommissionRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.CalcCardToCardTransferCommissionRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SPNameType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;

import java.math.BigDecimal;

/**
 * @author bogdanov
 * @ created 10.06.15
 * @ $Author$
 * @ $Revision$
 */

public class ESBMockP2PAutopayCommissionProcessor extends FederalESBMockProcessorBase<CalcCardToCardTransferCommissionRq, CalcCardToCardTransferCommissionRs>
{
	public ESBMockP2PAutopayCommissionProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected boolean needSendResult(ESBMessage<CalcCardToCardTransferCommissionRq> xmlRequest, CalcCardToCardTransferCommissionRs message)
	{
		return true;
	}

	@Override
	protected boolean needSendOnline(ESBMessage<CalcCardToCardTransferCommissionRq> xmlRequest, CalcCardToCardTransferCommissionRs message)
	{
		return true;
	}

	protected StatusType getStatus()
	{
		double rnd = Math.random();
		if (rnd < 0.7)
			return getStatusInstance(0, "", "", "");
		if (rnd < 0.8)
			return getStatusInstance(Math.round(-110 + 10*(rnd - 0.7)), "", "", "");
		return getRandomBoolean()? getOkStatus(): getErrorStatus();
	}

	@Override
	protected void process(ESBMessage<CalcCardToCardTransferCommissionRq> xmlRequest)
	{
		CalcCardToCardTransferCommissionRs responce = new CalcCardToCardTransferCommissionRs();
		responce.setOperUID(xmlRequest.getObject().getOperUID());
		responce.setRqTm(xmlRequest.getObject().getRqTm());
		responce.setRqUID(xmlRequest.getObject().getRqUID());
		responce.setStatus(getStatus());
		responce.setSPName(SPNameType.URN_SBRFSYSTEM_99_WAY);

		responce.setCommission(xmlRequest.getObject().getCurAmt().multiply(new BigDecimal(Math.random())));
		responce.setCommissionCur("810");

		send(xmlRequest, responce, "CalcCardToCardTransferCommission");
	}
}
