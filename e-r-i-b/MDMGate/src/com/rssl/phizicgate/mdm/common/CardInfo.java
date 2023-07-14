package com.rssl.phizicgate.mdm.common;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 16.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Информация по карте
 */

public class CardInfo
{
	private String number;
	private Calendar startDate;
	private Calendar expiredDate;

	/**
	 * @return номер
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * задать номер
	 * @param number номер
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * @return дата начала действия продукта
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * задать дату начала действия продукта
	 * @param startDate дата
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return дата завершения действия продукта
	 */
	public Calendar getExpiredDate()
	{
		return expiredDate;
	}

	/**
	 * задать дату завершения действия продукта
	 * @param expiredDate дата
	 */
	public void setExpiredDate(Calendar expiredDate)
	{
		this.expiredDate = expiredDate;
	}
}
