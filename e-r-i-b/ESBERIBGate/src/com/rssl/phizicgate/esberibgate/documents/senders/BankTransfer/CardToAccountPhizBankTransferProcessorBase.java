package com.rssl.phizicgate.esberibgate.documents.senders.BankTransfer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractCardTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AbstractPhizTransfer;
import com.rssl.phizicgate.esberibgate.documents.senders.XferMethodType;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.common.sender.XferAddMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.CustInfoType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.OperNameType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.XferInfo;

/**
 * @author akrenev
 * @ created 08.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Базовый билдер запроса перевода физическому лицу c карты на счет ФЛ
 */

abstract class CardToAccountPhizBankTransferProcessorBase<D extends AbstractPhizTransfer & AbstractCardTransfer> extends XferAddMessageProcessorBase<D>
{
	/**
	 * конструктор
	 * @param factory гейтовая фабрика
	 * @param document документ
	 */
	CardToAccountPhizBankTransferProcessorBase(GateFactory factory, D document, String serviceName)
	{
		super(factory, document, serviceName);
	}

	protected abstract XferMethodType getXferMethodType(D document) throws GateException;

	protected static OperNameType getOperationName()
	{
		return OperNameType.TCP;
	}

	@Override
	protected final XferInfo createBody(D document) throws GateException, GateLogicException
	{
		BackRefInfoRequestHelper helper = getRequestHelper();
		Client owner = helper.getBusinessOwner(document);
		Card fromCard = helper.getCard(owner, document.getChargeOffCard(), document.getOffice());

		XferInfo xferInfo = new XferInfo();

		xferInfo.setXferMethod(getXferMethodType(document).toValue());
		xferInfo.setTaxIdTo(document.getReceiverINN());

		xferInfo.setTaxIdFrom(owner.getINN());

		CustInfoType custInfo = new CustInfoType();
		custInfo.setPersonInfo(RequestHelper.getPersonInfo(document.getReceiverSurName(), document.getReceiverFirstName(), document.getReceiverPatrName()));
		xferInfo.setCustInfo(custInfo);

		xferInfo.setDepAcctIdTo(RequestHelper.createDepAcctId(document.getReceiverAccount(), document.getReceiverBank()));
		xferInfo.setPurpose(document.getGround());

		xferInfo.setCardAcctIdFrom(RequestHelper.createCardAcctId(fromCard, owner, document.getChargeOffCardExpireDate(), helper));
		return xferInfo;
	}
}
