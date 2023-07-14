package com.rssl.phizic.business.documents.payments.mock;

import com.rssl.phizic.gate.payments.AbstractJurTransfer;

/**
 * @author hudyakov
 * @ created 06.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class MockAbstractJurTransfer extends MockAbstractRUSPayment implements AbstractJurTransfer
{
	private String receiverName     = EMPTY_STRING;
	private String receiverKPP      = EMPTY_STRING;
	private String registerNumber   = EMPTY_STRING;
	private String registerString   = EMPTY_STRING;

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	public String getReceiverKPP()
	{
		return receiverKPP;
	}

	public void setReceiverKPP(String receiverKPP)
	{
		this.receiverKPP = receiverKPP;
	}

	public String getRegisterNumber()
	{
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber)
	{
		this.registerNumber = registerNumber;
	}

	public String getRegisterString()
	{
		return registerString;
	}

	public void setRegisterString(String registerString)
	{
		this.registerString = registerString;
	}
}
