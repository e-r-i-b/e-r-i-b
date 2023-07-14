package com.rssl.phizic.esb.ejb.mock.federal.StopAccountOperationClaim;

import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.federal.FederalESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SetAccountStateRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SetAccountStateRs;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * заглушка, имитирующая работу шины при запросе об утере сберегательной книжки
 */

public final class ESBMockStopAccountOperationProcessor extends FederalESBMockProcessorBase<SetAccountStateRq, SetAccountStateRs>
{
	/**
	 * конструктор
	 * @param module - модуль
	 */
	public ESBMockStopAccountOperationProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected boolean needSendResult(ESBMessage<SetAccountStateRq> xmlRequest, SetAccountStateRs message)
	{
		return getRandomBoolean();
	}

	@Override
	protected boolean needSendOnline(ESBMessage<SetAccountStateRq> xmlRequest, SetAccountStateRs message)
	{
		return getRandomBoolean();
	}

	@Override
	protected void process(ESBMessage<SetAccountStateRq> xmlRequest)
	{
		//Параметры запроса
		SetAccountStateRq request = xmlRequest.getObject();

		SetAccountStateRs setAccountStateRs = new SetAccountStateRs();
		setAccountStateRs.setOperUID(request.getOperUID());
		setAccountStateRs.setRqTm(request.getRqTm());
		setAccountStateRs.setRqUID(request.getRqUID());
		setAccountStateRs.setStatus(getRandomBoolean()? getOkStatus(): getErrorStatus());

		send(xmlRequest, setAccountStateRs, "StopDepositOperationClaim");
	}
}
