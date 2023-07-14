package com.rssl.auth.csa.back.integration.erib.types;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Контакт клиента
 */

public class Contact
{
	private String contactNum;
	private String contactType;

	/**
	 * @return значение контакта
	 */
	public String getContactNum()
	{
		return contactNum;
	}

	/**
	 * задать значение контакта
	 * @param contactNum значение контакта
	 */
	public void setContactNum(String contactNum)
	{
		this.contactNum = contactNum;
	}

	/**
	 * @return тип контакта
	 */
	public String getContactType()
	{
		return contactType;
	}

	/**
	 * задать тип контакта
	 * @param contactType тип контакта
	 */
	public void setContactType(String contactType)
	{
		this.contactType = contactType;
	}
}
