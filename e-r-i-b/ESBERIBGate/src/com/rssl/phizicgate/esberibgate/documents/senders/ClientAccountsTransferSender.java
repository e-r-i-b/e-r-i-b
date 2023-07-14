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
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizicgate.esberibgate.ws.generated.OperName_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.XferInfo_Type;

/**
 * ��������� TDD ������� �������� ������� �� ����� �� ������ �� ���� �� ������
 * ������������� ������ �������
 *
 * @author hudyakov
 * @ created 17.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class ClientAccountsTransferSender extends ConvertionSenderBase
{
	/**
	 * ctor
	 * @param factory �������
	 * @throws GateException
	 */
	public ClientAccountsTransferSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected boolean needRates(AbstractTransfer transfer)
	{
		return true;
	}

	protected Currency getDestinationCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof ClientAccountsTransfer))
			throw new GateException("�������� ��� ���������, ������ ���� - ClientAccountsTransfer.");

		ClientAccountsTransfer transfer = (ClientAccountsTransfer) document;
		return transfer.getDestinationCurrency();
	}

	public Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof ClientAccountsTransfer))
		{
			throw new GateException("�������� ��� ���������, ������ ���� - CardToAccountTransfer.");
		}
		ClientAccountsTransfer transfer = (ClientAccountsTransfer) document;
		return transfer.getChargeOffCurrency();
	}

	protected XferInfo_Type createBody(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof ClientAccountsTransfer))
		{
			throw new GateException("�������� ��� ���������, ������ ���� - AccountToCardTransfer.");
		}
		ClientAccountsTransfer transfer = (ClientAccountsTransfer) document;
		XferInfo_Type xferInfo = createConversationBody(transfer);
		Client owner = getBusinessOwner(transfer);
		xferInfo.setDepAcctIdFrom(createDepAcctIdFrom(getAccount(transfer.getChargeOffAccount(), document.getOffice()), owner));
		xferInfo.setDepAcctIdTo(createDepAcctId(getAccount(transfer.getReceiverAccount(), document.getOffice()), owner));

		return xferInfo;
	}

	protected XferInfo_Type createConversationBody(ClientAccountsTransfer transfer) throws GateException, GateLogicException
	{
		return super.createBody(transfer);
	}

	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return OperName_Type.TDD;
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		if(!(document instanceof ClientAccountsTransfer))
			throw new GateException("������������ ��� ���������, �������� ��������� ClientAccountsTransfer.");

		ClientAccountsTransfer transfer = (ClientAccountsTransfer) document;
		if(!isLongOfferMode())
			return;

		LongOffer longOffer = (LongOffer) document;
		if(!isSameTB(transfer.getOffice(), transfer.getReceiverAccount()))
		{
			// �������� �� ��, ��� � ���������� �������, ������� � ����� ����� "����� ����������� ��� ���������� ������� �� ����� ���������� ��
			// ��������� �����" ����������� ������ ������ ��.
			if(longOffer.getSumType() == SumType.REMAIND_IN_RECIP)
			{
				throw  new GateLogicException(REMAIND_IN_RECIP_MESSAGE);
			}
		}
	}
}
