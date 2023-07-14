package com.rssl.phizgate.mobilebank;

import com.rssl.phizic.gate.mobilebank.MobileBankCardInfo;
import com.rssl.phizic.gate.mobilebank.MobileBankCardStatus;

/**
 * @author Erkin
 * @ created 22.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class MobileBankCardInfoImpl implements MobileBankCardInfo
{
	private String cardNumber;

	private MobileBankCardStatus status;

	private String phoneNumber;

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public MobileBankCardStatus getStatus()
	{
		return status;
	}

	public void setStatus(MobileBankCardStatus status)
	{
		this.status = status;
	}

	public String getMobilePhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public int hashCode()
	{
		int result = cardNumber != null ? cardNumber.hashCode() : 0;
		result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
		return result;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MobileBankCardInfoImpl that = (MobileBankCardInfoImpl) o;

		if (cardNumber == null || that.cardNumber == null)
			return false;
		if (phoneNumber == null || that.phoneNumber == null)
			return false;

		if (!phoneNumber.equals(that.phoneNumber))
			return false;

		if (!cardNumber.equals(that.cardNumber))
			return false;

		return true;
	}

	public String toString()
	{
		return "MobileBankCardInfoImpl{" +
				"card=" + GateCardHelper.hideCardNumber(cardNumber) +
				", phoneNumber='" + GatePhoneHelper.hidePhoneNumber(phoneNumber) + '\'' +
				", status=" + status +
				'}';
	}
}
