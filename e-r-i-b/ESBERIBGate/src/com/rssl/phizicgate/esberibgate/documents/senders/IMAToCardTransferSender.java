package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.payments.IMAToCardTransfer;
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
 * Сендер для переводов ОМС -> карта
 */

public class IMAToCardTransferSender extends CardIMATransferSenderBase
{
	public IMAToCardTransferSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected Currency getDestinationCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAToCardTransfer))
			throw new GateException("Неверный тип документа, должен быть - IMAToCardTransfer.");

		IMAToCardTransfer transfer = (IMAToCardTransfer) document;
		return transfer.getDestinationCurrency();
	}

	public Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAToCardTransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - IMAToCardTransfer.");
		}
		IMAToCardTransfer transfer = (IMAToCardTransfer) document;
		return transfer.getChargeOffCurrency();
	}

	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return OperName_Type.TIC;
	}

	/**
	 * сформировать запрос на исполнение документа.
	 * @param document данные о документе
	 * @return запрос
	 */
	public IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAToCardTransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - IMAToCardTransfer, а пришел " + document.getClass());
		}

		IMAToCardTransfer transfer = (IMAToCardTransfer) document;

		IMAToCardAddRq_Type imaToCardAddRq = new IMAToCardAddRq_Type();
//заполянем ключевые поля
		imaToCardAddRq.setRqUID(PaymentsRequestHelper.generateUUID());
		imaToCardAddRq.setRqTm(PaymentsRequestHelper.generateRqTm());
		imaToCardAddRq.setOperUID(PaymentsRequestHelper.generateOUUID());
		imaToCardAddRq.setSPName(SPName_Type.BP_ERIB);
		imaToCardAddRq.setBankInfo(paymentsRequestHelper.createAuthBankInfo(transfer.getInternalOwnerId()));
//получаем тело сообщения
		XferIMAInfo_Type xferIMAInfo = new XferIMAInfo_Type();
		Client owner = getBusinessOwner(transfer);
		xferIMAInfo.setIMAAcctIdFrom(paymentsRequestHelper.createIMAAcctId(getIMAccount(owner, transfer.getChargeOffAccount()), owner));
		xferIMAInfo.setCardAcctIdTo(createCardAcctId(getCard(owner, transfer.getReceiverCard(), transfer.getOffice()), owner, transfer.getReceiverCardExpireDate()));
		xferIMAInfo.setPurpose(transfer.getGround());
		xferIMAInfo.setCurAmt(transfer.getChargeOffAmount().getDecimal());
		xferIMAInfo.setAcctCur(transfer.getChargeOffAmount().getCurrency().getCode());
		xferIMAInfo.setCurAmtConv(getCurAmtConvType(transfer));
		xferIMAInfo.setExecute(true);
		imaToCardAddRq.setXferInfo(xferIMAInfo);
		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setIMAToCardAddRq(imaToCardAddRq);
		return ifxRq;
	}

	protected IFXRq_Type createRequest(GateDocument document, RequestHelperBase requestHelper) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAToCardTransfer))
		{
			throw new GateException("Ожидается IMAToCardTransfer");
		}
		IMAToCardTransfer claim = (IMAToCardTransfer) document;
		XferOperStatusInfoRq_Type request = ((PaymentsRequestHelper)requestHelper).createXferOperStatusInfoRq(claim);

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

	protected String getSystemId(IMAToCardTransfer claim) throws GateLogicException, GateException
	{
		Client owner = getBusinessOwner(claim);
		IMAccount imAccount = getIMAccount(owner, claim.getChargeOffAccount());
		IMAAcctId_Type imaAcctId = paymentsRequestHelper.createIMAAcctId(imAccount, owner);

		return imaAcctId.getSystemId();
	}

	protected Status_Type getStatusType(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getStatus() : ifxRs.getIMAToCardAddRs().getStatus();
	}

	protected Object getMainInfo(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs() : ifxRs.getIMAToCardAddRs();
	}

	protected String getExternalId(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getRqUID() : ifxRs.getIMAToCardAddRs().getRqUID();
	}

	protected String getOperUid(IFXRs_Type ifxRs)
	{
		return ifxRs.getIMAToCardAddRs().getOperUID();
	}

	protected Calendar getOperTime(IFXRs_Type ifxRs)
	{
		String rqTm = ifxRs.getIMAToCardAddRs().getRqTm();
		return parseCalendar(rqTm);
	}
}
