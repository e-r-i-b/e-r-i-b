package com.rssl.phizic.business.documents.decorators;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AccountJurIntraBankTransfer;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.common.forms.doc.CreationType;

import java.util.Calendar;
import java.util.List;

/**
 * @author gladishev
 * @ created 22.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class TaxTransitPaymentDecorator extends PaymentDecoratorBase implements AccountJurIntraBankTransfer
{
	RurPayment original;

	public TaxTransitPaymentDecorator(RurPayment original)
	{
		super(original);
		this.original = original;
	}

	public String getReceiverName()
	{
		return original.getReceiverName();
	}

	public void setReceiverName(String receiverName)
	{
		original.setReceiverName(receiverName);
	}

	public String getReceiverSurName()
	{
		return original.getReceiverSurName();
	}

	public String getReceiverFirstName()
	{
		return original.getReceiverFirstName();
	}

	public String getReceiverPatrName()
	{
		return original.getReceiverPatrName();
	}

	public String getReceiverAccount()
	{
		return original.getTransitBankAccount();
	}

	public Currency getDestinationCurrency() throws GateException
	{
		return original.getDestinationCurrency();
	}

	public ResidentBank getReceiverBank()
	{
		return original.getReceiverBank();
	}

	public String getReceiverBIC()
	{
		return original.getTransitBankBIC();
	}

	public String getReceiverCorAccount()
	{
		return original.getTransitBankCorAccount();
	}

	public String getReceiverINN()
	{
		return original.getReceiverINN();
	}

	public String getReceiverKPP()
	{
		return original.getReceiverKPP();
	}

	public String getReceiverBankName()
	{
		return original.getTransitBankName();
	}

	public String getReceiverAlias()
	{
		return original.getReceiverAlias();
	}

	public void setGround(String value)
	{
		original.setGround(value);
	}

	public String getPayerName()
	{
		return original.getPayerName();
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{
		original.setTariffPlanESB(tariffPlanESB);
	}

	public Calendar getClientOperationDate()
	{
		return original.getClientOperationDate();
	}

	public void setClientOperationDate(Calendar clientOperationDate)
	{
		original.setClientOperationDate(clientOperationDate);
	}

	public Calendar getAdditionalOperationDate()
	{
		return original.getAdditionalOperationDate();
	}

	public Class<? extends GateDocument> getType()
	{
		return AccountJurIntraBankTransfer.class;
	}

	public FormType getFormType()
	{
		return original.getFormType();
	}

	public EmployeeInfo getCreatedEmployeeInfo() throws GateException
	{
		return original.getCreatedEmployeeInfo();
	}

	public EmployeeInfo getConfirmedEmployeeInfo() throws GateException
	{
		return original.getConfirmedEmployeeInfo();
	}

	public CreationType getClientCreationChannel()
	{
		return original.getClientCreationChannel();
	}

	public CreationType getClientOperationChannel()
	{
		return original.getClientOperationChannel();
	}

	public CreationType getAdditionalOperationChannel()
	{
		return original.getAdditionalOperationChannel();
	}

	public List<WriteDownOperation> getWriteDownOperations()
	{
		return original.getWriteDownOperations();
	}

	public void setWriteDownOperations(List<WriteDownOperation> list)
	{
		original.setWriteDownOperations(list);
	}

	public String getNextState()
	{
		throw new UnsupportedOperationException();
	}

	public void setNextState(String nextState)
	{
		throw new UnsupportedOperationException();
	}

	public void setRegisterNumber(String registerNumber)
	{
		original.setRegisterNumber(registerNumber);
	}

	public void setRegisterString(String registerString)
	{
		original.setRegisterString(registerString);
	}

	public InputSumType getInputSumType()
	{
		return original.getInputSumType();
	}

	public String getOperationCode()
	{
		return original.getOperationCode();
	}

	public Long getSendNodeNumber()
	{
		return original.getSendNodeNumber();
	}

	public void setSendNodeNumber(Long nodeNumber)
	{
		original.setSendNodeNumber(nodeNumber);
	}

	public Currency getChargeOffCurrency() throws GateException
	{
		return original.getChargeOffCurrency();
	}
}
