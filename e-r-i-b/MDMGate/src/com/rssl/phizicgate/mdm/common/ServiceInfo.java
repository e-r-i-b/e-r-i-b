package com.rssl.phizicgate.mdm.common;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 16.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Информация по сервису
 */

public class ServiceInfo
{
	private String type;
	private Calendar startDate;
	private Calendar endDate;

	/**
	 * @return тип
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * задать тип
	 * @param type тип
	 */
	public void setType(String type)
	{
		this.type = type;
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
	public Calendar getEndDate()
	{
		return endDate;
	}

	/**
	 * задать дату окончания действия
	 * @param endDate дата
	 */
	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}
}
