package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.monitoring.MonitoringLogHelper;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.OperName_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.Status_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.XferInfo_Type;

/**
 * @author krenev
 * @ created 16.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class AccountToCardTransferSender extends ConvertionSenderBase
{
	public AccountToCardTransferSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected boolean needRates(AbstractTransfer transfer)
	{
		return true;
	}

	public Currency getDestinationCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountToCardTransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - AccountToCardTransfer.");
		}
		AccountToCardTransfer transfer = (AccountToCardTransfer) document;
		return transfer.getDestinationCurrency();
	}

	public Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountToCardTransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - AccountToCardTransfer.");
		}
		AccountToCardTransfer transfer = (AccountToCardTransfer) document;
		return transfer.getChargeOffCurrency();
	}

	protected XferInfo_Type createBody(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountToCardTransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - AccountToCardTransfer.");
		}
		AccountToCardTransfer transfer = (AccountToCardTransfer) document;
		XferInfo_Type xferInfo = super.createBody(transfer);
		Client owner = getBusinessOwner(transfer);
		xferInfo.setDepAcctIdFrom(createDepAcctIdFrom(getAccount(transfer.getChargeOffAccount(), document.getOffice()), owner));
		xferInfo.setCardAcctIdTo(createCardAcctId(getCard(owner, transfer.getReceiverCard(), document.getOffice()), owner, transfer.getReceiverCardExpireDate()));

		return xferInfo;
	}

	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return OperName_Type.TDC;
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		Status_Type statusType = ifxRs.getXferAddRs().getStatus();
		long statusCode = statusType.getStatusCode();
		if (statusCode == 0L)
			return;

		MonitoringLogHelper.writeAccountToCardTransferErrorLog(document, Long.toString(statusCode));

		super.processResponse(document, ifxRs);
	}
}
