package com.rssl.phizicgate.esberibgate.documents.senders.InternalTransfer;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.common.sender.XferAddMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.DepAcctIdType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.OperNameType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.XferAddRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.XferInfo;

/**
 * @author akrenev
 * @ created 03.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Ѕилдер запроса перевода со вклада на вклад одного клиента
 */

class AccountToAccountTransferProcessor extends XferAddMessageProcessorBase<ClientAccountsTransfer>
{
	/**
	 * конструктор
	 * @param factory гейтова€ фабрика
	 * @param document документ
	 * @param serviceName им€ сервиса
	 */
	AccountToAccountTransferProcessor(GateFactory factory, ClientAccountsTransfer document, String serviceName)
	{
		super(factory, document, serviceName);
	}

	static OperNameType getOperationName()
	{
		return OperNameType.TDD;
	}

	@Override
	protected OperNameType getOperationName(ClientAccountsTransfer document)
	{
		return getOperationName();
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return getOperationName().value();
	}

	@Override
	protected XferInfo createBody(ClientAccountsTransfer document) throws GateException, GateLogicException
	{
		XferInfo xferInfo = new XferInfo();
		Currency chargeOffCurrency = document.getChargeOffCurrency();
		Currency destinationCurrency = document.getDestinationCurrency();

		if (!chargeOffCurrency.compare(destinationCurrency))
			xferInfo.setCurAmtConv(getCurAmtConvType(document));

		BackRefInfoRequestHelper helper = getRequestHelper();

		Client owner = helper.getBusinessOwner(document);
		Account fromAccount = helper.getAccount(document.getChargeOffAccount(), document.getOffice());
		Account toAccount = helper.getAccount(document.getReceiverAccount(), document.getOffice());

		DepAcctIdType depAcctId = RequestHelper.createDepAcctId(fromAccount, owner, helper);
		if (StringHelper.isEmpty(depAcctId.getSystemId()))
			throw new GateLogicException("¬ы не можете выполнить перевод с этого счета. ѕожалуйста, выберите другой счет списани€.");

		xferInfo.setDepAcctIdFrom(depAcctId);
		xferInfo.setDepAcctIdTo(RequestHelper.createDepAcctId(toAccount, owner, helper));
		return xferInfo;
	}

	@Override
	protected void updateDocumentRequestData(XferAddRq request, ClientAccountsTransfer document){}

	protected Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof ClientAccountsTransfer))
		{
			throw new GateException("Ќеверный тип документа, должен быть - ClientAccountsTransfer.");
		}
		ClientAccountsTransfer transfer = (ClientAccountsTransfer) document;
		return transfer.getChargeOffCurrency();
	}
}
