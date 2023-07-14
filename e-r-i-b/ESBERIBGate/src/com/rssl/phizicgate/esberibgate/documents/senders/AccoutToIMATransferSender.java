package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AccountToIMATransfer;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

/**
 *
 * ������������ ��� �������� ����� -> ���
 *
 * User: Balovtsev
 * Date: 28.06.2011
 * Time: 16:18:20
 *
 */
public class AccoutToIMATransferSender extends AccountIMATransferSenderBase
{
	/**
	 * @param factory �������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public AccoutToIMATransferSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return OperName_Type.TDI;
	}

	public Currency getDestinationCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountToIMATransfer))
		{
			throw new GateException("�������� ��� ���������, ������ ���� - AccountToIMATransfer.");
		}
		ClientAccountsTransfer transfer = (ClientAccountsTransfer) document;
		return transfer.getDestinationCurrency();
	}

	protected XferInfo_Type createBody(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountToIMATransfer))
		{
			throw new GateException("�������� ��� ���������, ������ ���� - AccountToIMATransfer.");
		}
		ClientAccountsTransfer transfer = (ClientAccountsTransfer) document;
		XferInfo_Type xferInfo = createConversationBody(transfer);
		Client owner = getBusinessOwner(transfer);
		xferInfo.setDepAcctIdFrom(createDepAcctIdFrom(getAccount(transfer.getChargeOffAccount(), document.getOffice()), owner));
		xferInfo.setDepAcctIdTo(paymentsRequestHelper.createDepAcctId(getIMAccount(owner, transfer.getReceiverAccount()), owner));
		return xferInfo;
	}

	protected void fillAmount(AbstractTransfer transfer, XferInfo_Type xferInfo) throws GateException
	{
		if (!(transfer instanceof AccountToIMATransfer))
		{
			throw new GateException("�������� ��� ���������, ������ ���� - AccountToIMATransfer.");
		}

		// ������ �������� ����� ���
		Money amount = transfer.getDestinationAmount();

		if (amount == null)
			throw new GateException("�� ������ ����� �������");

		fillAmount(xferInfo, amount);
	}

	protected IFXRq_Type createRequest(GateDocument document, RequestHelperBase requestHelper) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountToIMATransfer))
		{
			throw new GateException("��������� AccountToIMATransfer");
		}
		AccountToIMATransfer claim = (AccountToIMATransfer) document;
		XferOperStatusInfoRq_Type request = PaymentsRequestHelper.createXferOperStatusInfoRq(claim);

		request.setSystemId(getSystemId(claim));
		request.setBankInfo(getBankInfo(claim));
		request.setOperName(getOperationName(claim));

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setXferOperStatusInfoRq(request);
		return ifxRq;
	}

	protected String getSystemId(AccountToIMATransfer claim) throws GateLogicException, GateException
	{
		Client owner = getBusinessOwner(claim);
		Account account = getAccount(claim.getChargeOffAccount(), claim.getOffice());
		DepAcctId_Type depAcctId = createDepAcctIdFrom(account, owner);
		return depAcctId.getSystemId();
	}
}
