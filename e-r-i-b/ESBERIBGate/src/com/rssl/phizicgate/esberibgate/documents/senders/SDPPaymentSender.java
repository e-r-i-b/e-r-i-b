package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

/**
 * Ѕазовый сендре дл€ команды SDP:
 * создание длительного поручени€ на перевод денежных средств с вклада физическому лицу
 * @author krenev
 * @ created 22.10.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class SDPPaymentSender extends AbstractRUSPaymentSender
{
	public SDPPaymentSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected boolean needRates(AbstractTransfer transfer)
	{
		return false;
	}

	public Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AbstractAccountTransfer))
		{
			throw new GateException("Ќеверный тип документа, должен быть - AbstractAccountTransfer.");
		}
		AbstractAccountTransfer transfer = (AbstractAccountTransfer) document;
		return getAccountCurrency(transfer.getChargeOffAccount());
	}

	public IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AbstractAccountTransfer))
		{
			throw new GateException("Ќеверный тип документа, должен быть - AbstractAccountTransfer.");
		}
		AbstractAccountTransfer transfer = (AbstractAccountTransfer) document;
		SvcAddRq_Type svcAddRq= new SvcAddRq_Type();
		svcAddRq.setRqUID(PaymentsRequestHelper.generateUUID());
		svcAddRq.setRqTm(PaymentsRequestHelper.generateRqTm());
		svcAddRq.setOperUID(PaymentsRequestHelper.generateOUUID());
		svcAddRq.setSPName(SPName_Type.BP_ERIB);
		svcAddRq.setOperName(getOperationName(transfer));
		svcAddRq.setBankInfo(paymentsRequestHelper.createAuthBankInfo(transfer.getInternalOwnerId()));

		XferInfo_Type xferInfo = createBody(transfer);
		fillAmount(transfer, xferInfo);

		svcAddRq.setXferInfo(xferInfo);
		svcAddRq.setRegular(paymentsRequestHelper.getRegular((LongOffer) transfer));

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setSvcAddRq(svcAddRq);
		return ifxRq;
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		Status_Type statusType = ifxRs.getSvcAddRs().getStatus();
		long statusCode = statusType.getStatusCode();
		if (statusCode != 0)
		{
			//¬се ошибки пользовательские. ≈сли описание ошибки не пришло, то выдаем сообщение по умолчанию
			throwGateLogicException(statusType, SvcAddRs_Type.class);
		}
		if (document instanceof SynchronizableDocument)
		{

			SynchronizableDocument synchronizableDocument = (SynchronizableDocument) document;
			synchronizableDocument.setExternalId(ifxRs.getSvcAddRs().getRqUID());
		}
	}

	protected XferInfo_Type createBody(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AbstractAccountTransfer))
		{
			throw new GateException("Ќеверный тип документа, должен быть - AbstractAccountTransfer.");
		}
		AbstractAccountTransfer transfer = (AbstractAccountTransfer) document;

		XferInfo_Type xferInfo = super.createBody(document);
		//запол€нем источник списани€
		xferInfo.setDepAcctIdFrom(createDepAcctIdFrom(getAccount(transfer.getChargeOffAccount(), transfer.getOffice()), getBusinessOwner(transfer)));
		return xferInfo;
	}

	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return OperName_Type.SDP;
	}

	protected void fillAmount(XferInfo_Type xferInfo, Money amount)
	{
		xferInfo.setCurAmt1(amount.getDecimal());
		xferInfo.setAcctCur1(amount.getCurrency().getCode());
	}

	protected ReceiverType getReceiverType()
	{
		return ReceiverType.PHIZ;
	}
}
