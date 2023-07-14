package com.rssl.phizic.business.documents.payments.mock;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;
import com.rssl.phizic.gate.dictionaries.ResidentBank;

/**
 * @author osminin
 * @ created 02.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockAbstractRUSPayment extends MockAbstractTransfer implements AbstractRUSPayment
{
	private String receiverSurName = EMPTY_STRING;
	private String receiverFirstName = EMPTY_STRING;
	private String receiverPatrName = EMPTY_STRING;
	private String receiverAccount = EMPTY_STRING;
	private String receiverBIC = EMPTY_STRING;
	private String receiverCorAccount = EMPTY_STRING;
	private String receiverINN = EMPTY_STRING;
	private String receiverBankName = EMPTY_STRING;
	private String receiverAlias = EMPTY_STRING;

	public String getReceiverSurName()
	{
		return receiverSurName;
	}

	public String getReceiverFirstName()
	{
		return receiverFirstName;
	}

	public String getReceiverPatrName()
	{
		return receiverPatrName;
	}

	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	public Currency getDestinationCurrency() throws GateException
	{
		return null;
	}

	public String getReceiverBIC()
	{
		return receiverBIC;
	}

	public String getReceiverCorAccount()
	{
		return receiverCorAccount;
	}

	public String getReceiverINN()
	{
		return receiverINN;
	}

	public String getReceiverBankName()
	{
		return receiverBankName;
	}

	public String getReceiverAlias()
	{
		return receiverAlias;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public void setReceiverBIC(String receiverBIC)
	{
		this.receiverBIC = receiverBIC;
	}

	public void setReceiverCorAccount(String receiverCorAccount)
	{
		this.receiverCorAccount = receiverCorAccount;
	}

	public void setReceiverINN(String receiverINN)
	{
		this.receiverINN = receiverINN;
	}

	public void setReceiverBankName(String receiverBankName)
	{
		this.receiverBankName = receiverBankName;
	}

	public void setReceiverAlias(String receiverAlias)
	{
		this.receiverAlias = receiverAlias;
	}

	public void setReceiverSurName(String receiverSurName)
	{
		this.receiverSurName = receiverSurName;
	}

	public void setReceiverFirstName(String receiverFirstName)
	{
		this.receiverFirstName = receiverFirstName;
	}

	public void setReceiverPatrName(String receiverPatrName)
	{
		this.receiverPatrName = receiverPatrName;
	}

	public ResidentBank getReceiverBank()
	{
		return new ResidentBank();
	}

	public String getReceiverName()
	{
		return null; 
	}

	public void setReceiverBank(ResidentBank receiverBank)
	{

	}
}
