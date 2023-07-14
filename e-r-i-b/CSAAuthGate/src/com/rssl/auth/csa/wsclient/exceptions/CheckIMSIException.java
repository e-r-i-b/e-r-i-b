package com.rssl.auth.csa.wsclient.exceptions;

/**
 * Ошибка проверки IMSI
 * @author Jatsky
 * @ created 28.01.15
 * @ $Author$
 * @ $Revision$
 */

public class CheckIMSIException extends BackLogicException
{
	private String phones;

	public CheckIMSIException(String message, String phones)
	{
		super(message);
		this.phones = phones;
	}

	public String getPhones()
	{
		return phones;
	}
}