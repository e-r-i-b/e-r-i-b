package com.rssl.phizicgate.mdm.common;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 16.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Информация по вкладу/счету
 */

public class AccountInfo
{
	private String number;
	private Calendar startDate;
	private Calendar closingDate;

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
	 * @return дата начала действия
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * задать дату начала действия
	 * @param startDate дата
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return дата окончания действия
	 */
	public Calendar getClosingDate()
	{
		return closingDate;
	}

	/**
	 * задать дату окончания действия
	 * @param closingDate дата
	 */
	public void setClosingDate(Calendar closingDate)
	{
		this.closingDate = closingDate;
	}
}
