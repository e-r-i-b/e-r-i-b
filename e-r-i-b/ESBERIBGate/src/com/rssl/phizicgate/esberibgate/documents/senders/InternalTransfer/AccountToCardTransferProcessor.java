package com.rssl.phizicgate.esberibgate.documents.senders.InternalTransfer;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.monitoring.MonitoringLogHelper;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.common.sender.XferAddMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

/**
 * @author akrenev
 * @ created 03.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Ѕилдер запроса перевода со вклада на карту одного клиента
 */

class AccountToCardTransferProcessor extends XferAddMessageProcessorBase<AccountToCardTransfer>
{
	/**
	 * конструктор
	 * @param factory гейтова€ фабрика
	 * @param document документ
	 * @param serviceName им€ сервиса
	 */
	AccountToCardTransferProcessor(GateFactory factory, AccountToCardTransfer document, String serviceName)
	{
		super(factory, document, serviceName);
	}

	static OperNameType getOperationName()
	{
		return OperNameType.TDC;
	}

	@Override
	protected OperNameType getOperationName(AccountToCardTransfer document)
	{
		return getOperationName();
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return getOperationName().value();
	}

	@Override
	protected XferInfo createBody(AccountToCardTransfer document) throws GateException, GateLogicException
	{
		XferInfo xferInfo = new XferInfo();

		Currency chargeOffCurrency = document.getChargeOffCurrency();
		Currency destinationCurrency = document.getDestinationCurrency();

		if (!chargeOffCurrency.compare(destinationCurrency))
			xferInfo.setCurAmtConv(getCurAmtConvType(document));

		BackRefInfoRequestHelper helper = getRequestHelper();

		Client owner = helper.getBusinessOwner(document);
		Account account = helper.getAccount(document.getChargeOffAccount(), document.getOffice());
		Card card = helper.getCard(owner, document.getReceiverCard(), document.getOffice());

		DepAcctIdType depAcctId = RequestHelper.createDepAcctId(account, owner, helper);
		if (StringHelper.isEmpty(depAcctId.getSystemId()))
			throw new GateLogicException("¬ы не можете выполнить перевод с этого счета. ѕожалуйста, выберите другой счет списани€.");

		xferInfo.setDepAcctIdFrom(depAcctId);
		xferInfo.setCardAcctIdTo(RequestHelper.createCardAcctId(card, owner, document.getReceiverCardExpireDate(), helper));
		return xferInfo;
	}

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<XferAddRs>> request, Response<XferAddRs> response) throws GateException, GateLogicException
	{
		if (!OK_CODE.equals(response.getErrorCode()))
			MonitoringLogHelper.writeAccountToCardTransferErrorLog(getDocument(), response.getErrorCode());

		super.processResponse(request, response);
	}

	@Override
	protected void updateDocumentRequestData(XferAddRq request, AccountToCardTransfer document){}

	protected Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountToCardTransfer))
		{
			throw new GateException("Ќеверный тип документа, должен быть - AccountToCardTransfer.");
		}
		AccountToCardTransfer transfer = (AccountToCardTransfer) document;
		return transfer.getChargeOffCurrency();
	}
}
