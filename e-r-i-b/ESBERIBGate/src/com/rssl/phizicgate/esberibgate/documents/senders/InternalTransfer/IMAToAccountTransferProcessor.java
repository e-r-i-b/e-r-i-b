package com.rssl.phizicgate.esberibgate.documents.senders.InternalTransfer;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.payments.IMAToAccountTransfer;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.common.sender.XferAddMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.OperNameType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.XferAddRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.XferInfo;

/**
 * @author akrenev
 * @ created 03.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на перевод с обезличенного металлического счета на вклад
 */

class IMAToAccountTransferProcessor extends XferAddMessageProcessorBase<IMAToAccountTransfer>
{
	/**
	 * конструктор
	 * @param factory гейтовая фабрика
	 * @param document документ
	 * @param serviceName имя сервиса
	 */
	IMAToAccountTransferProcessor(GateFactory factory, IMAToAccountTransfer document, String serviceName)
	{
		super(factory, document, serviceName);
	}

	private static OperNameType getOperationName()
	{
		return OperNameType.TID;
	}

	@Override
	protected OperNameType getOperationName(IMAToAccountTransfer document)
	{
		return getOperationName();
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return getOperationName().value();
	}

	@Override
	protected XferInfo createBody(IMAToAccountTransfer document) throws GateException, GateLogicException
	{
		XferInfo xferInfo = new XferInfo();
		Currency chargeOffCurrency = document.getChargeOffCurrency();
		Currency destinationCurrency = document.getDestinationCurrency();

		if (!chargeOffCurrency.compare(destinationCurrency))
			xferInfo.setCurAmtConv(getCurAmtConvType(document));
		BackRefInfoRequestHelper helper = getRequestHelper();

		Client owner = helper.getBusinessOwner(document);
		IMAccount fromAccount = helper.getIMAccount(owner, document.getChargeOffAccount());
		Account toAccount = helper.getAccount(document.getReceiverAccount(), document.getOffice());

		xferInfo.setDepAcctIdFrom(RequestHelper.createDepAcctId(fromAccount, owner));
		xferInfo.setDepAcctIdTo(RequestHelper.createDepAcctId(toAccount, owner, helper));

		return xferInfo;
	}

	@Override
	protected void updateDocumentRequestData(XferAddRq request, IMAToAccountTransfer document)
	{
		document.setOperUId(request.getOperUID());
		document.setOperTime(RequestHelper.parseCalendar(request.getRqTm()));
	}

	protected Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAToAccountTransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - IMAToAccountTransfer.");
		}
		IMAToAccountTransfer transfer = (IMAToAccountTransfer) document;
		return transfer.getChargeOffCurrency();
	}
}
