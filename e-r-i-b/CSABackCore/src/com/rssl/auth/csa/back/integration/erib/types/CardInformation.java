package com.rssl.auth.csa.back.integration.erib.types;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Информация о карте
 */

public class CardInformation
{
	private String number;

	/**
	 * конструктор
	 */
	public CardInformation()
	{}

	/**
	 * конструктор
	 * @param number номер карты
	 */
	public CardInformation(String number)
	{
		this.number = number;
	}

	/**
	 * @return номер карты
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * задать номер карты
	 * @param number номер карты
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}
}
