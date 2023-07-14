package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.dictionaries.receivers.personal.PaymentReceiverXmlSerializer;

/**
 * @author Egorova
 * @ created 16.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class PaymentReceiverPhiz extends PaymentReceiverBase
{
	private String account;
	private String INN;
	private String bankName;
	private String correspondentAccount;
	private String bankCode;
	private String codeType; //BIC или SWIFT

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getINN()
	{
		return INN;
	}

	public void setINN(String INN)
	{
		this.INN = INN;
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public String getCorrespondentAccount()
	{
		return correspondentAccount;
	}

	public void setCorrespondentAccount(String correspondentAccount)
	{
		this.correspondentAccount = correspondentAccount;
	}

	public String getBankCode()
	{
		return bankCode;
	}

	public void setBankCode(String bankCode)
	{
		this.bankCode = bankCode;
	}

	public String getCodeType()
	{
		return codeType;
	}

	public void setCodeType(String codeType)
	{
		this.codeType = codeType;
	}

	public PaymentReceiverXmlSerializer getXmlSerializer()
	{
		return new PaymentReceiverXmlSerializer(this);
	}
}
