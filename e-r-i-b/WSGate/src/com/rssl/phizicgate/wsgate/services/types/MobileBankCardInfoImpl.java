package com.rssl.phizicgate.wsgate.services.types;

import com.rssl.phizgate.mobilebank.GateCardHelper;
import com.rssl.phizgate.mobilebank.GatePhoneHelper;
import com.rssl.phizic.gate.mobilebank.MobileBankCardInfo;
import com.rssl.phizic.gate.mobilebank.MobileBankCardStatus;

/**
 * @author Jatsky
 * @ created 21.05.15
 * @ $Author$
 * @ $Revision$
 */

public class MobileBankCardInfoImpl implements MobileBankCardInfo
{
	private String cardNumber;

	private MobileBankCardStatus status;

	private String mobilePhoneNumber;

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

	public void setStatus(String status)
	{
		if(status == null || status.trim().length() == 0)
			return;
		this.status = Enum.valueOf(MobileBankCardStatus.class, status);
	}

	public String getMobilePhoneNumber()
	{
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber)
	{
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public int hashCode()
	{
		int result = cardNumber != null ? cardNumber.hashCode() : 0;
		result = 31 * result + (mobilePhoneNumber != null ? mobilePhoneNumber.hashCode() : 0);
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
		if (mobilePhoneNumber == null || that.mobilePhoneNumber == null)
			return false;

		if (!mobilePhoneNumber.equals(that.mobilePhoneNumber))
			return false;

		if (!cardNumber.equals(that.cardNumber))
			return false;

		return true;
	}

	public String toString()
	{
		return "MobileBankCardInfoImpl{" +
				"card=" + GateCardHelper.hideCardNumber(cardNumber) +
				", phoneNumber='" + GatePhoneHelper.hidePhoneNumber(mobilePhoneNumber) + '\'' +
				", status=" + status +
				'}';
	}
}
