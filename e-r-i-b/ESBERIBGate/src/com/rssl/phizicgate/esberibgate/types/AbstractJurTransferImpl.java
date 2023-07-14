package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AbstractJurTransfer;
import com.rssl.phizic.gate.longoffer.LongOffer;

/**
 * @author khudyakov
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class AbstractJurTransferImpl extends AbstractRUSPaymentImpl implements AbstractJurTransfer
{
	private String receiverName;
	private String receiverKPP;
	private String registerNumber;
	private String registerString;
	private Currency chargeOffCurrency;

	public AbstractJurTransferImpl()
	{
	}

	public AbstractJurTransferImpl(LongOffer longOffer) throws GateException
	{
		super(longOffer);
	}

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

	public Currency getChargeOffCurrency() throws GateException
	{
		return chargeOffCurrency;
	}

	public void setChargeOffCurrency(Currency chargeOffCurrency)
	{
		this.chargeOffCurrency = chargeOffCurrency;
	}
}
