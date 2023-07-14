package com.rssl.phizicgate.mobilebank;

import javax.xml.bind.annotation.*;

/**
 * @author Jatsky
 * @ created 07.08.15
 * @ $Author$
 * @ $Revision$
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PHONE")
public class RegistrationInfoPhone
{

	/**
	 * Номер телефона
	 */
	@XmlValue
	private String phoneNumber;

	/**
	 * Список мобильных операторов (зарезервировано)
	 */
	@XmlAttribute(name = "OPERATOR")
	private String mobileOperator;

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getMobileOperator()
	{
		return mobileOperator;
	}

	public void setMobileOperator(String mobileOperator)
	{
		this.mobileOperator = mobileOperator;
	}
}
