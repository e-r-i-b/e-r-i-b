package com.rssl.phizic.business.ext.sbrf.payments.forms;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.gate.dictionaries.Bank;

/**
 * @author Omeliyanchuk
 * @ created 16.10.2006
 * @ $Author$
 * @ $Revision$
 */

public class MemOrder
{
	private BusinessDocument payment = null;
	private Person person = null;
	private String receiverINN = "";
	private String receiverKPP = "";

	private Bank payerBank = null;
	private Bank reciverBank = null;

	public Bank getPayerBank()
	{
		return payerBank;
	}

	public void setPayerBank(Bank bank)
	{
		this.payerBank = bank;
	}

	public Bank getReciverBank()
	{
		return reciverBank;
	}

	public void setReciverBank(Bank bank)
	{
		this.reciverBank = bank;
	}

	public String getreceiverINN()
	{
		return receiverINN;
	}
	public void setreceiverINN(String receiverINN)
	{
		this.receiverINN = receiverINN;
	}
	public String getreceiverKPP()
	{
		return receiverKPP;
	}
	public void setreceiverKPP(String receiverKPP)
	{
		this.receiverKPP = receiverKPP;
	}

	public Person getPerson()
	{
		return person;
	}
	public void setPerson(Person person)
	{
		this.person = person;
	}
	public BusinessDocument getPayment()
	{
		return payment;
	}
	public void setPayment(BusinessDocument payment)
	{
		this.payment = payment;
	}
}
