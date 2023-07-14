package com.rssl.phizicgate.esberibgate.documents.senders.InternalTransfer;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.CardToAccountTransfer;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.common.sender.XferAddMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.OperNameType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.XferAddRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.XferInfo;

/**
 * @author akrenev
 * @ created 02.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Ѕилдер запроса перевода с карты на вклад одного клиента
 */

class CardToAccountTransferProcessor extends XferAddMessageProcessorBase<CardToAccountTransfer>
{
	/**
	 * конструктор
	 * @param factory гейтова€ фабрика
	 * @param document документ
	 * @param serviceName им€ сервиса
	 */
	CardToAccountTransferProcessor(GateFactory factory, CardToAccountTransfer document, String serviceName)
	{
		super(factory, document, serviceName);
	}

	private static OperNameType getOperationName()
	{
		return OperNameType.TCD;
	}

	@Override
	protected OperNameType getOperationName(CardToAccountTransfer document)
	{
		return getOperationName();
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return getOperationName().value();
	}

	@Override
	protected XferInfo createBody(CardToAccountTransfer document) throws GateException, GateLogicException
	{
		XferInfo xferInfo = new XferInfo();
		BackRefInfoRequestHelper helper = getRequestHelper();

		Client owner = helper.getBusinessOwner(document);
		Card card = helper.getCard(owner, document.getChargeOffCard(), document.getOffice());
		Account account = helper.getAccount(document.getReceiverAccount(), document.getOffice());

		xferInfo.setCardAcctIdFrom(RequestHelper.createCardAcctId(card, owner, document.getChargeOffCardExpireDate(), helper));
		xferInfo.setDepAcctIdTo(RequestHelper.createDepAcctId(account, owner, helper));
		return xferInfo;
	}

	@Override
	protected void updateDocumentRequestData(XferAddRq request, CardToAccountTransfer document){}

	protected Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof CardToAccountTransfer))
		{
			throw new GateException("Ќеверный тип документа, должен быть - CardToAccountTransfer.");
		}
		CardToAccountTransfer transfer = (CardToAccountTransfer) document;
		return transfer.getChargeOffCurrency();
	}
}
