package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.CardToAccountTransfer;
import com.rssl.phizicgate.esberibgate.ws.generated.OperName_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.XferInfo_Type;

/**
 * @author krenev
 * @ created 16.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardToAccountTransferSender extends ConvertionSenderBase
{
	public CardToAccountTransferSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected boolean needRates(AbstractTransfer transfer)
	{
		return false;
	}

	public Currency getDestinationCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof CardToAccountTransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - CardToAccountTransfer.");
		}
		CardToAccountTransfer transfer = (CardToAccountTransfer) document;
		return transfer.getDestinationCurrency();
	}

	public Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof CardToAccountTransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - CardToAccountTransfer.");
		}
		CardToAccountTransfer transfer = (CardToAccountTransfer) document;
		return transfer.getChargeOffCurrency();
	}

	protected XferInfo_Type createBody(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof CardToAccountTransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - CardToAccountTransfer.");
		}
		CardToAccountTransfer transfer = (CardToAccountTransfer) document;
		XferInfo_Type xferInfo = super.createBody(transfer);
		Client owner = getBusinessOwner(transfer);
		xferInfo.setCardAcctIdFrom(createCardAcctId(getCard(owner, transfer.getChargeOffCard(), transfer.getOffice()), owner, transfer.getChargeOffCardExpireDate()));
		xferInfo.setDepAcctIdTo(createDepAcctId(getAccount(transfer.getReceiverAccount(), transfer.getOffice()), owner));
		return xferInfo;
	}

	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return OperName_Type.TCD;
	}


	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		if(!(document instanceof CardToAccountTransfer))
			throw new GateException("Некорректный тип документа, ожидался наследник CardToAccountTransfer.");

		CardToAccountTransfer transfer = (CardToAccountTransfer) document;
		if(!isLongOfferMode())
			return;

		LongOffer longOffer = (LongOffer) document;
		if(!isSameTB(transfer.getOffice(), transfer.getReceiverAccount()))
		{
			// проверка на то, что в регулярной платеже, перевод с типом суммы "сумма необходимая для увеличения остатка на счете получателя до
			// указанной суммы" совершается внутри одного ТБ.
			if(longOffer.getSumType() == SumType.REMAIND_IN_RECIP)
			{
				throw  new GateLogicException(REMAIND_IN_RECIP_MESSAGE);
			}
		}
	}
}

