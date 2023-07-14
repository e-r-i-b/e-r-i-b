package com.rssl.phizic.gate.ima;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * Не найден ОМС
 * @author Pankin
 * @ created 01.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountNotFoundException extends GateLogicException
{
	private String imAccountNumber;

	/**
	 *
	 * @param imAccountNumber номер
	 */
	public IMAccountNotFoundException(String imAccountNumber)
	{
		super("Металлический счет № " + imAccountNumber + " не найден");
		this.imAccountNumber = imAccountNumber;
	}

	public String getIMAccountNumber()
	{
		return imAccountNumber;
	}
}
