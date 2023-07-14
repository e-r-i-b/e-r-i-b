package com.rssl.phizicgate.mock.depo;

import com.rssl.phizic.gate.depo.DepoDebtItemInfo;

/**
 * @author mihaylov
 * @ created 20.08.2010
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

	public DepoDebtItemInfoImpl(String bankBIC, String bankName, String bankCorAccount, String recipientName, String recipientINN, String recipientKPP, String recipientAccount)
	{
		this.bankBIC = bankBIC;
		this.bankName = bankName;
		this.bankCorAccount = bankCorAccount;
		this.recipientName = recipientName;
		this.recipientINN = recipientINN;
		this.recipientKPP = recipientKPP;
		this.recipientAccount = recipientAccount;
	}

	public String getBankBIC()
	{
		return bankBIC;
	}

	public String getBankName()
	{
		return bankName;
	}

	public String getBankCorAccount()
	{
		return bankCorAccount;
	}

	public String getRecipientName()
	{
		return recipientName;
	}

	public String getRecipientINN()
	{
		return recipientINN;
	}

	public String getRecipientKPP()
	{
		return recipientKPP;
	}

	public String getRecipientAccount()
	{
		return recipientAccount;
	}
}
