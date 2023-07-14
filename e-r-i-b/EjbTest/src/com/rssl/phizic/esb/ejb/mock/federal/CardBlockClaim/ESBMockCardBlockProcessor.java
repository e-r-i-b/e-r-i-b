package com.rssl.phizic.esb.ejb.mock.federal.CardBlockClaim;

import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.federal.FederalESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.CardBlockRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.CardBlockRs;

/**
 * @author akrenev
 * @ created 26.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * заглушка, имитирующая работу шины при блокировке карты
 */

public final class ESBMockCardBlockProcessor extends FederalESBMockProcessorBase<CardBlockRq, CardBlockRs>
{
	/**
	 * конструктор
	 * @param module - модуль
	 */
	public ESBMockCardBlockProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected boolean needSendResult(ESBMessage<CardBlockRq> xmlRequest, CardBlockRs message)
	{
		return getRandomBoolean();
	}

	@Override
	protected boolean needSendOnline(ESBMessage<CardBlockRq> xmlRequest, CardBlockRs message)
	{
		return getRandomBoolean();
	}

	@Override
	protected void process(ESBMessage<CardBlockRq> xmlRequest)
	{
		CardBlockRq cardBlockRq = xmlRequest.getObject();
		CardBlockRs cardBlockRs = new CardBlockRs();

		cardBlockRs.setRqUID(cardBlockRq.getRqUID());
		cardBlockRs.setRqTm(cardBlockRq.getRqTm());
		cardBlockRs.setOperUID(cardBlockRq.getOperUID());
		cardBlockRs.setStatus(getRandomBoolean()? getOkStatus(): getErrorStatus());

		send(xmlRequest, cardBlockRs, "BlockingCardClaim");
	}
}
