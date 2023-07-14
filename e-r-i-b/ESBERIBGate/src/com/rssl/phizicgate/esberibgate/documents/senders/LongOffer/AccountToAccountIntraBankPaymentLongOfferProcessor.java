package com.rssl.phizicgate.esberibgate.documents.senders.LongOffer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.longoffer.AccountRUSPaymentLongOffer;
import com.rssl.phizicgate.esberibgate.documents.senders.XferMethodType;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SvcAddRs;

/**
 * @author akrenev
 * @ created 08.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Ѕилдер запроса на перевод физическому лицу со вклада на счет в другой банк через платежную систему –оссии(ƒлительное поручение).
 */

class AccountToAccountIntraBankPaymentLongOfferProcessor extends AccountToAccountBankPaymentLongOfferProcessorBase<AccountRUSPaymentLongOffer>
{
	private static final String OK_CODE = "0";

	/**
	 * конструктор
	 * @param factory гейтова€ фабрика
	 * @param document документ
	 * @param serviceName им€ сервиса
	 */
	AccountToAccountIntraBankPaymentLongOfferProcessor(GateFactory factory, AccountRUSPaymentLongOffer document, String serviceName)
	{
		super(factory, document, serviceName);
	}

	@Override
	protected XferMethodType getXferMethod(AccountRUSPaymentLongOffer document)
	{
		return XferMethodType.EXTERNAL_BANK;
	}

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<SvcAddRs>> request, Response<SvcAddRs> response) throws GateException, GateLogicException
	{
		if (!OK_CODE.equals(response.getErrorCode()))
			processError(request, response);
	}
}
