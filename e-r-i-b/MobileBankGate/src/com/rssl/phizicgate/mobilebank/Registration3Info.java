package com.rssl.phizicgate.mobilebank;

import static com.rssl.phizgate.mobilebank.GateCardHelper.hideCardNumber;
import static com.rssl.phizic.utils.PhoneNumberUtil.getCutPhoneNumber;

import java.util.Arrays;

/**
 * @author osminin
 * @ created 16.05.2013
 * @ $Author$
 * @ $Revision$
 *
 * Атрибуты регистрации, возвращаемой хранимой процедурой getRegistration3
 */
public class Registration3Info extends RegistrationInfo
{
	private String date;    // дата последней регистрации
	private String src;     // источник регистрации

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getSrc()
	{
		return src;
	}

	public void setSrc(String src)
	{
		this.src = src;
	}

	public String toString()
	{
		return "RegistrationInfo{" +
				"cardNumber='" + hideCardNumber(getCardNumber()) + '\'' +
				", status='" + getStatus() + '\'' +
				", tariff='" + getTariff() + '\'' +
				", phoneNumber='" + getCutPhoneNumber(getPhoneNumber()) + '\'' +
				", mobileOperator='" + getMobileOperator() + '\'' +
				", date='" + date + '\'' +
				", src='" + src + '\'' +
				", cardList='" + Arrays.toString(getCardList().entrySet().toArray()) + '\'' +
				'}';
	}
}
