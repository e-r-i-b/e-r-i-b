package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;
import com.rssl.phizic.gate.payments.AbstractPhizTransfer;
import com.rssl.phizic.gate.payments.AbstractJurTransfer;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

/**
 * @author krenev
 * @ created 22.10.2010
 * @ $Author$
 * @ $Revision$
 * ������� ������ ��� ��������� �� ������������ ����������
 */
public abstract class AbstractRUSPaymentSender extends ConvertionSenderBase
{

	/**
	 * ctor
	 * @param factory �������
	 * @throws GateException
	 */
	public AbstractRUSPaymentSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	/**
	 * ��������� ��������� ���������
	 * ������ �������� ���������� ����������
	 * @param document ������ �������
	 * @return ���� �������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	protected XferInfo_Type createBody(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AbstractRUSPayment))
			throw new GateException("�������� ��� ���������, ������ ���� - AbstractRUSPayment.");

		AbstractRUSPayment transfer = (AbstractRUSPayment) document;

		XferInfo_Type xferInfo = super.createBody(transfer);
		xferInfo.setXferMethod(getXferMethod(transfer).toValue());
		xferInfo.setTaxIdTo(transfer.getReceiverINN());

		Client owner = getBusinessOwner(transfer);
		xferInfo.setTaxIdFrom(owner.getINN());

		ReceiverType receiverType = getReceiverType();

		if (ReceiverType.PHIZ == receiverType)
		{
			AbstractPhizTransfer phizTransfer = (AbstractPhizTransfer) transfer;
			xferInfo.setCustInfo(createCustInfo(phizTransfer));
		}

		if (ReceiverType.JUR == receiverType)
		{
			AbstractJurTransfer jurTransfer = (AbstractJurTransfer) transfer;
			xferInfo.setRecipientName(jurTransfer.getReceiverName());
			xferInfo.setKPPTo(jurTransfer.getReceiverKPP());
		}	
		
		xferInfo.setDepAcctIdTo(createDepAcctId(transfer));
		xferInfo.setPurpose(transfer.getGround());

		return xferInfo;
	}

	/**
	 * @param document ��������
	 * @return ���� ������������ ������� (������ �����, � ������ ���� ���������, � ������ ���. ����)
	 */
	protected abstract XferMethodType getXferMethod(AbstractRUSPayment document) throws GateException;

	/**
	 * @return ��� ���������� ������� (PHIZ/JUR)
	 */
	protected abstract ReceiverType getReceiverType();

	/**
	 * ��������� ��p������ ���������� �� ����� ����������.
	 * @param document �������
	 * @return DepAcctId_Type
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	protected DepAcctId_Type createDepAcctId(AbstractRUSPayment document) throws GateLogicException, GateException
	{
		DepAcctId_Type result = new DepAcctId_Type();
		result.setAcctId(document.getReceiverAccount());
		ResidentBank residentBank = document.getReceiverBank();
		result.setBIC(residentBank.getBIC());
		result.setCorrAcctId(residentBank.getAccount());
		return result;
	}

	/**
	 * ��������� ��������� �ustInfo_type
	 * @param document ��������
	 * @return CustInfo_type
	 */
	protected CustInfo_Type createCustInfo(AbstractPhizTransfer document)
	{
		CustInfo_Type custInfo_type = new CustInfo_Type();
		PersonInfo_Type personInfo_type = new PersonInfo_Type();
		PersonName_Type personName_type = new PersonName_Type();

		personName_type.setFirstName(document.getReceiverFirstName());
		personName_type.setLastName(document.getReceiverSurName());
		personName_type.setMiddleName(document.getReceiverPatrName());

		personInfo_type.setPersonName(personName_type);
		custInfo_type.setPersonInfo(personInfo_type);

		return custInfo_type;
	}

	public Currency getDestinationCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AbstractRUSPayment))
		{
			throw new GateException("�������� ��� ���������, ������ ���� - AbstractRUSPayment.");
		}
		AbstractRUSPayment transfer = (AbstractRUSPayment) document;
		//��� �������� ����� ������ ����� �� ������
		return getAccountCurrency(transfer.getReceiverAccount());
	}

}
