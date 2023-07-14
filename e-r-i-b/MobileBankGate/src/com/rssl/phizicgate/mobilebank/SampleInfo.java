package com.rssl.phizicgate.mobilebank;

import static com.rssl.phizgate.mobilebank.GateCardHelper.hideCardNumber;
import com.rssl.phizic.utils.PhoneNumberUtil;

import java.util.*;

/**
 * @author Erkin
 * @ created 21.04.2010
 * @ $Author$
 * @ $Revision$
 */
class SampleInfo
{
	private String cardNumber;

	private String cardStatus;

	private String tariff;

	private String phoneNumber;

	private String mobileOperator;

	/**
	 * Меп "код_получателя -> список_кодов_плательщика"
	 */
	private final Map<String, Set<String>> destList
			= new LinkedHashMap<String, Set<String>>();

	private final List<String> operatorCodenames = new ArrayList<String>();

	///////////////////////////////////////////////////////////////////////////

	String getCardNumber()
	{
		return cardNumber;
	}

	void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	String getCardStatus()
	{
		return cardStatus;
	}

	void setCardStatus(String cardStatus)
	{
		this.cardStatus = cardStatus;
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

	Map<String, Set<String>> getDestList()
	{
		return Collections.unmodifiableMap(destList);
	}

	void setDestList(Map<String, Set<String>> destList)
	{
		this.destList.clear();
		this.destList.putAll(destList);
	}

	List<String> getOperatorCodenames()
	{
		return Collections.unmodifiableList(operatorCodenames);
	}

	void setOperatorCodenames(List<String> operatorCodenames)
	{
		this.operatorCodenames.clear();
		this.operatorCodenames.addAll(operatorCodenames);
	}

	public String toString()
	{
		return "SampleInfo{" +
				"cardNumber=" + hideCardNumber(cardNumber) +
				", cardStatus=" + cardStatus +
				", tariff='" + tariff + '\'' +
				", phoneNumber='" + PhoneNumberUtil.getCutPhoneNumber(phoneNumber) + '\'' +
				", mobileOperator='" + mobileOperator + '\'' +
				", destList=" + Arrays.toString(destList.entrySet().toArray()) +
				", operatorCodenames=" + Arrays.toString(operatorCodenames.toArray()) +
				'}';
	}
}