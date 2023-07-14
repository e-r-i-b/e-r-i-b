package com.rssl.phizic.gate.confirmation;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Информация о карточке, как об источнике подтверждения
 */

public class CardConfirmationSourceImpl implements CardConfirmationSource
{
	private String number;

	/**
	 * конструктор
	 */
	public CardConfirmationSourceImpl()
	{}

	/**
	 * конструктор
	 * @param number номер карты
	 */
	public CardConfirmationSourceImpl(String number)
	{
		this.number = number;
	}

	/**
	 * задать номер карты
	 * @param number номер карты
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getNumber()
	{
		return number;
	}
}
