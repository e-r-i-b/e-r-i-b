package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.gate.payments.IMAToAccountTransfer;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

/**
 *
 * ������������ ��� �������� ��� -> �����
 *
 * User: Balovtsev
 * Date: 28.06.2011
 * Time: 16:20:22
 */
public class IMAToAccoutTransferSender extends AccountIMATransferSenderBase
{
	/**
	 * @param factory �������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public IMAToAccoutTransferSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return OperName_Type.TID;
	}

	public Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAToAccountTransfer))
		{
			throw new GateException("�������� ��� ���������, ������ ���� - IMAToAccountTransfer.");
		}
		ClientAccountsTransfer transfer = (ClientAccountsTransfer) document;
		return transfer.getChargeOffCurrency();
	}

	protected XferInfo_Type createBody(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAToAccountTransfer))
		{
			throw new GateException("�������� ��� ���������, ������ ���� - IMAToAccountTransfer.");
		}
		ClientAccountsTransfer transfer = (ClientAccountsTransfer) document;
		XferInfo_Type xferInfo = createConversationBody(transfer);
		Client owner = getBusinessOwner(transfer);
		xferInfo.setDepAcctIdFrom(paymentsRequestHelper.createDepAcctId(getIMAccount(owner, transfer.getChargeOffAccount()), owner));
		xferInfo.setDepAcctIdTo(createDepAcctId(getAccount(transfer.getReceiverAccount(), document.getOffice()), owner));
		return xferInfo;
	}

	protected void fillAmount(AbstractTransfer transfer, XferInfo_Type xferInfo) throws GateException
	{
		if (!(transfer instanceof IMAToAccountTransfer))
		{
			throw new GateException("�������� ��� ���������, ������ ���� - IMAToAccountTransfer.");
		}

		// ������ �������� ����� ���
		Money amount = transfer.getChargeOffAmount();

		if (amount == null)
			throw new GateException("�� ������ ����� �������");

		fillAmount(xferInfo, amount);
	}

	protected IFXRq_Type createRequest(GateDocument document, RequestHelperBase requestHelper) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAToAccountTransfer))
		{
			throw new GateException("��������� IMAToAccountTransfer");
		}
		IMAToAccountTransfer claim = (IMAToAccountTransfer) document;
		XferOperStatusInfoRq_Type request = ((PaymentsRequestHelper)requestHelper).createXferOperStatusInfoRq(claim);

		request.setSystemId(getSystemId(claim));
		request.setBankInfo(getBankInfo(claim));
		request.setOperName(getOperationName(claim));

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setXferOperStatusInfoRq(request);
		return ifxRq;
	}

	protected String getSystemId(IMAToAccountTransfer claim) throws GateLogicException, GateException
	{
		Client owner = getBusinessOwner(claim);
		IMAccount imAccount = getIMAccount(owner, claim.getChargeOffAccount());
		DepAcctId_Type depAcctId = paymentsRequestHelper.createDepAcctId(imAccount, owner);
		return depAcctId.getSystemId();
	}
}
