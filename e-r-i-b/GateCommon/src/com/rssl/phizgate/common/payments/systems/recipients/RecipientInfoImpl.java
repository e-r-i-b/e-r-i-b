package com.rssl.phizgate.common.payments.systems.recipients;

import com.rssl.phizic.gate.payments.systems.recipients.RecipientInfo;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * @author egorova
 * @ created 29.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class RecipientInfoImpl implements RecipientInfo
{
	private String INN;
	private String KPP;
	private String account;
	private ResidentBank bank;
	private String transitAccount;

	public RecipientInfoImpl()
	{
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

	public String getINN()
	{
		return INN;
	}

	public String getKPP()
	{
		return KPP;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public void setBank(ResidentBank bank)
	{
		this.bank = bank;
	}

	public void setINN(String INN)
	{
		this.INN = INN;
	}

	public void setKPP(String KPP)
	{
		this.KPP = KPP;
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
