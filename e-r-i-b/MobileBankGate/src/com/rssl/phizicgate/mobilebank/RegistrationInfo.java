package com.rssl.phizicgate.mobilebank;

import static com.rssl.phizgate.mobilebank.GateCardHelper.hideCardNumber;
import static com.rssl.phizic.utils.PhoneNumberUtil.getCutPhoneNumber;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 20.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� �����������
 */
class RegistrationInfo
{
	/**
	 * ����� �������� �����
	 */
	private String cardNumber;

	/**
	 * ������ �����������
	 */
	private String status;

	/**
	 * �����
	 */
	private String tariff;

	/**
	 * ����� ��������
	 */
	private String phoneNumber;

	/**
	 * ������ ��������� ���������� (���������������)
	 */
	private String mobileOperator;

	/**
	 * ��� "�����_���������_����� -> ������"
	 */
	private Map<String, String> cardList;

	///////////////////////////////////////////////////////////////////////////

	String getCardNumber()
	{
		return cardNumber;
	}

	void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	String getStatus()
	{
		return status;
	}

	void setStatus(String status)
	{
		this.status = status;
	}

	String getTariff()
	{
		return tariff;
	}

	void setTariff(String tariff)
	{
		this.tariff = tariff;
	}

	String getPhoneNumber()
	{
		return phoneNumber;
	}

	void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	String getMobileOperator()
	{
		return mobileOperator;
	}

	void setMobileOperator(String mobileOperator)
	{
		this.mobileOperator = mobileOperator;
	}

	Map<String, String> getCardList()
	{
		if (cardList == null)
			return null;
		return new LinkedHashMap<String, String>(cardList);
	}

	void setCardList(Map<String, String> cardList)
	{
		if (cardList == null)
			this.cardList = null;
		this.cardList = new LinkedHashMap<String, String>(cardList);
	}

	public String toString()
	{
		return "RegistrationInfo{" +
				"cardNumber='" + hideCardNumber(cardNumber) + '\'' +
				", status='" + status + '\'' +
				", tariff='" + tariff + '\'' +
				", phoneNumber='" + getCutPhoneNumber(phoneNumber) + '\'' +
				", mobileOperator='" + mobileOperator + '\'' +
				", cardList='" + Arrays.toString(cardList.entrySet().toArray()) + '\'' +
				'}';
	}
}