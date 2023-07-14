package com.rssl.phizicgate.mdm.common;

/**
 * @author akrenev
 * @ created 16.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Информация по счету депо
 */

public class DepoAccountInfo
{
	private String number;

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
}
