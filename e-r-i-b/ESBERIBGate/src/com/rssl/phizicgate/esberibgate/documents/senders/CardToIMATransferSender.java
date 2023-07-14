package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.payments.CardToIMATransfer;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;

/**
 * @author Ismagilova
 * @ created 11.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * Сендер для переводов карта -> ОМС
 */

public class CardToIMATransferSender extends CardIMATransferSenderBase
{
	public CardToIMATransferSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected Currency getDestinationCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof CardToIMATransfer))
			throw new GateException("Неверный тип документа, должен быть - CardToIMATransfer.");
		
		CardToIMATransfer transfer = (CardToIMATransfer) document;
		return transfer.getDestinationCurrency();
	}

	public Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof CardToIMATransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - CardToIMATransfer.");
		}
		CardToIMATransfer transfer = (CardToIMATransfer) document;
		return transfer.getChargeOffCurrency();
	}

/**
	 * сформировать запрос на исполнение документа.
	 * Для платежей это всегда XferAddRq.
	 * Заполянеяем заголовки и общие поля.
	 * @param document данные о документе
	 * @return запрос
	 */
	public IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof CardToIMATransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - CardToIMATransfer.");
		}
		CardToIMATransfer transfer = (CardToIMATransfer) document;

		CardToIMAAddRq_Type cardToIMAAddRq = new CardToIMAAddRq_Type();
//заполянем ключевые поля
		cardToIMAAddRq.setRqUID(PaymentsRequestHelper.generateUUID());
		cardToIMAAddRq.setRqTm(PaymentsRequestHelper.generateRqTm());
		cardToIMAAddRq.setOperUID(PaymentsRequestHelper.generateOUUID());
		cardToIMAAddRq.setSPName(SPName_Type.BP_ERIB);

		cardToIMAAddRq.setBankInfo(paymentsRequestHelper.createAuthBankInfo(transfer.getInternalOwnerId()));
//получаем тело сообщения
		XferIMAInfo_Type xferIMAInfo = new XferIMAInfo_Type();
		Client owner = getBusinessOwner(transfer);
		xferIMAInfo.setCardAcctIdFrom(createCardAcctId(getCard(owner, transfer.getChargeOffCard(), transfer.getOffice()), owner, transfer.getChargeOffCardExpireDate()));
		xferIMAInfo.setIMAAcctIdTo(paymentsRequestHelper.createIMAAcctId(getIMAccount(owner, transfer.getReceiverAccount()), owner));
		xferIMAInfo.setPurpose(transfer.getGround());
		xferIMAInfo.setCurAmt(transfer.getDestinationAmount().getDecimal());
		xferIMAInfo.setAcctCur(transfer.getDestinationAmount().getCurrency().getCode());

		xferIMAInfo.setCurAmtConv(getCurAmtConvType(transfer));
		xferIMAInfo.setExecute(true);

		cardToIMAAddRq.setXferInfo(xferIMAInfo);

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setCardToIMAAddRq(cardToIMAAddRq);
		return ifxRq;
	}

	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return OperName_Type.TCI;
	}

	protected IFXRq_Type createRequest(GateDocument document, RequestHelperBase requestHelper) throws GateException, GateLogicException
	{
		if (!(document instanceof CardToIMATransfer))
		{
			throw new GateException("Ожидается CardToIMATransfer");
		}
		CardToIMATransfer claim = (CardToIMATransfer) document;
		XferOperStatusInfoRq_Type request = PaymentsRequestHelper.createXferOperStatusInfoRq(claim);

		request.setSystemId(getSystemId(claim));
		BankInfo_Type bankInfo_Type = paymentsRequestHelper.createAuthBankInfo(claim.getInternalOwnerId());
		request.setBankInfo(bankInfo_Type);
		request.setOperName(getOperationName(claim));

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setXferOperStatusInfoRq(request);
		return ifxRq;
	}

	public IFXRq_Type createRepeatExecRequest(GateDocument document) throws GateException, GateLogicException
	{
		return createRequest(document, paymentsRequestHelper);
	}

	protected String getSystemId(CardToIMATransfer claim) throws GateLogicException, GateException
	{
		Client owner = getBusinessOwner(claim);
		IMAccount imAccount = getIMAccount(owner, claim.getReceiverAccount());
		IMAAcctId_Type imaAcctId = paymentsRequestHelper.createIMAAcctId(imAccount, owner);

		return imaAcctId.getSystemId();
	}

	protected Status_Type getStatusType(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getStatus() : ifxRs.getCardToIMAAddRs().getStatus();
	}

	protected Object getMainInfo(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs() : ifxRs.getCardToIMAAddRs();
	}

	protected String getExternalId(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getRqUID(): ifxRs.getCardToIMAAddRs().getRqUID();
	}

	protected String getOperUid(IFXRs_Type ifxRs)
	{
		return ifxRs.getCardToIMAAddRs().getOperUID();
	}

	protected Calendar getOperTime(IFXRs_Type ifxRs)
	{
		String rqTm = ifxRs.getCardToIMAAddRs().getRqTm();
		return parseCalendar(rqTm);
	}
}
