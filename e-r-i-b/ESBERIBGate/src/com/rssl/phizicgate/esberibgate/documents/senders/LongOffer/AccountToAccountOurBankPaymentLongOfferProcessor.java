package com.rssl.phizicgate.esberibgate.documents.senders.LongOffer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.longoffer.AccountIntraBankPaymentLongOffer;
import com.rssl.phizicgate.esberibgate.documents.senders.XferMethodType;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
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
 * Билдер запроса на перевод физическому лицу со вклада на счет в другой банк внутри Сбербанка России(Длительное поручение).
 */

class AccountToAccountOurBankPaymentLongOfferProcessor extends AccountToAccountBankPaymentLongOfferProcessorBase<AccountIntraBankPaymentLongOffer>
{
	private static final String OK_CODE = "0";

	/**
	 * конструктор
	 * @param factory гейтовая фабрика
	 * @param document документ
	 * @param serviceName имя сервиса
	 */
	AccountToAccountOurBankPaymentLongOfferProcessor(GateFactory factory, AccountIntraBankPaymentLongOffer document, String serviceName)
	{
		super(factory, document, serviceName);
	}

	@Override
	protected XferMethodType getXferMethod(AccountIntraBankPaymentLongOffer document) throws GateException
	{
		if (BackRefInfoRequestHelper.isSameTB(document.getOffice(), document.getReceiverAccount()))
			return XferMethodType.OUR_TERBANK;

		return XferMethodType.EXTERNAL_TERBANK;
	}

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<SvcAddRs>> request, Response<SvcAddRs> response) throws GateException, GateLogicException
	{
		if (!OK_CODE.equals(response.getErrorCode()))
			processError(request, response);
	}
}
