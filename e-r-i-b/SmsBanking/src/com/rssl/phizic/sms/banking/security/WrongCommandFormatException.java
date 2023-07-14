package com.rssl.phizic.sms.banking.security;

/**
 * @author emakarov
 * @ created 10.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class WrongCommandFormatException extends UserSendException
{
	/**
	 * их много и они есть
	 */
	public WrongCommandFormatException()
	{
		super("Ќеверный формат команды");
	}
}
