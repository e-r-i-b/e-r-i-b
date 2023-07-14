package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.depo.DepoDebtItemInfo;

/**
 * @author mihaylov
 * @ created 05.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoDebtItemInfoImpl implements DepoDebtItemInfo
{
	private String bankBIC;
	private String bankName;
	private String bankCorAccount;
	private String recipientName;
	private String recipientINN;
	private String recipientKPP;
	private String recipientAccount;

	public String getBankBIC()
	{
		return bankBIC;
	}

	public void setBankBIC(String bankBIC)
	{
		this.bankBIC = bankBIC;
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public String getBankCorAccount()
	{
		return bankCorAccount;
	}

	public void setBankCorAccount(String bankCorAccount)
	{
		this.bankCorAccount = bankCorAccount;
	}

	public String getRecipientName()
	{
		return recipientName;
	}

	public void setRecipientName(String recipientName)
	{
		this.recipientName = recipientName;
	}

	public String getRecipientINN()
	{
		return recipientINN;
	}

	public void setRecipientINN(String recipientINN)
	{
		this.recipientINN = recipientINN;
	}

	public String getRecipientKPP()
	{
		return recipientKPP;
	}

	public void setRecipientKPP(String recipientKPP)
	{
		this.recipientKPP = recipientKPP;
	}

	public String getRecipientAccount()
	{
		return recipientAccount;
	}

	public void setRecipientAccount(String recipientAccount)
	{
		this.recipientAccount = recipientAccount;
	}
}
