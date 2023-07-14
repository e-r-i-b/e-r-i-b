package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.phizic.gate.mobilebank.MobileBankCardInfo;
import com.rssl.phizic.gate.mobilebank.MobileBankCardStatus;
import com.rssl.phizic.utils.MaskUtil;

/**
 * @author Erkin
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class BusinessMobileBankCardInfo implements MobileBankCardInfo
{
	private String cardNumber;

	private MobileBankCardStatus status;

	private String mobilePhoneNumber;

	public BusinessMobileBankCardInfo() {}

	public BusinessMobileBankCardInfo(String cardNumber, MobileBankCardStatus status, String mobilePhoneNumber)
	{
		setCardNumber(cardNumber);
		setStatus(status);
		setMobilePhoneNumber(mobilePhoneNumber);
	}

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

		BusinessMobileBankCardInfo that = (BusinessMobileBankCardInfo) o;

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
		return "BusinessMobileBankCardInfo{" +
				"card=" + MaskUtil.getCutCardNumberForLog(cardNumber) +
				", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
				", status=" + status +
				'}';
	}
}
