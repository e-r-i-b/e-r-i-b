package com.rssl.auth.csa.wsclient.exceptions;

/**
 * Исключение, сигнализирующее о невозможности отправки СМС из-за подмены SIM-карты
 * @author Pankin
 * @ created 28.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class SendSmsMessageException extends BackLogicException
{
	private String phones;

	public SendSmsMessageException(String message, String phones)
	{
		super(message);
		this.phones = phones;
	}

	public String getPhones()
	{
		return phones;
	}
}
