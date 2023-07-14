package com.rssl.phizic.web.gate.services.documents.types;

/**
 * @author egorova
 * @ created 28.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class RecipientInfoImpl
{
	private String inn;
	private String kpp;
	private String account;
	private String payerAccount;
	private DebtImpl debt;
	private ResidentBank bank;
	private String paymentType;

	public String getPaymentType()
	{
		return paymentType;
	}

	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}

	public RecipientInfoImpl()
	{
		account = "";
		bank = new ResidentBank();
	}

	public String getAccount()
	{
		return account;
	}

	public ResidentBank getBank()
	{
		return bank;
	}

	public DebtImpl getDebt()
	{
		return debt;
	}

	public String getINN()
	{
		return inn;
	}

	public String getKPP()
	{
		return kpp;
	}

	public String getPayerAccount()
	{
		return payerAccount;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public void setBank(ResidentBank bank)
	{
		this.bank = bank;
	}

	public void setDebt(DebtImpl debt)
	{
		this.debt = debt;
	}

	public void setInn(String inn)
	{
		this.inn = inn;
	}

	public void setKpp(String kpp)
	{
		this.kpp = kpp;
	}

	public void setPayerAccount(String payerAccount)
	{
		this.payerAccount = payerAccount;
	}
}
