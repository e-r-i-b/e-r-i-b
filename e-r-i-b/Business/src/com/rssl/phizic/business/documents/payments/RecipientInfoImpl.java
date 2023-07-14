package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.payments.systems.recipients.RecipientInfo;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * @author Gainanov
 * @ created 16.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class RecipientInfoImpl implements RecipientInfo
{
	private String inn;
	private String kpp;
	private String account;
	private ResidentBank bank;
	private String transitAccount;

	public String getAccount()
	{
		return account;
	}

	public ResidentBank getBank()
	{
		return bank;
	}

	public String getINN()
	{
		return inn;
	}

	public String getKPP()
	{
		return kpp;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public void setBank(ResidentBank bank)
	{
		this.bank = bank;
	}

	public void setInn(String inn)
	{
		this.inn = inn;
	}

	public void setKpp(String kpp)
	{
		this.kpp = kpp;
	}

	public String getTransitAccount()
	{
		return transitAccount;
	}

	public void setTransitAccount(String transitAccount)
	{
		this.transitAccount = transitAccount;
	}
}

