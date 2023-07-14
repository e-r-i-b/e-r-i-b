package com.rssl.phizgate.mobilebank;

import com.rssl.phizic.gate.mobilebank.MobileBankCardInfo;
import com.rssl.phizic.gate.mobilebank.MobileBankTemplate;

import java.util.Arrays;

/**
 * @author Erkin
 * @ created 22.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class MobileBankTemplateImpl implements MobileBankTemplate
{
	private MobileBankCardInfo cardInfo;

	private String recipient;

	private String[] payerCodes;

	///////////////////////////////////////////////////////////////////////////

	public MobileBankCardInfo getCardInfo()
	{
		return cardInfo;
	}

	public void setCardInfo(MobileBankCardInfo cardInfo)
	{
		this.cardInfo = cardInfo;
	}

	public String getRecipient()
	{
		return recipient;
	}

	public void setRecipient(String recipient)
	{
		this.recipient = recipient;
	}

	public String[] getPayerCodes()
	{
		if (payerCodes == null)
			return null;
		return payerCodes.clone();
	}

	public void setPayerCodes(String[] payerCodes)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.payerCodes = payerCodes;
	}

	public int hashCode()
	{
		return recipient != null ? recipient.hashCode() : 0;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MobileBankTemplateImpl that = (MobileBankTemplateImpl) o;

		if (this.recipient == null || that.recipient == null)
			return false;
		if (this.payerCodes == null || that.payerCodes == null)
			return false;

		return recipient.equals(that.recipient)
				&& Arrays.equals(payerCodes, that.payerCodes);
	}

	public String toString()
	{
		return "MobileBankTemplateImpl{" +
				"recipient='" + recipient + '\'' +
				", payerCodes=" + (payerCodes == null ? null : Arrays.asList(payerCodes)) +
				'}';
	}
}
