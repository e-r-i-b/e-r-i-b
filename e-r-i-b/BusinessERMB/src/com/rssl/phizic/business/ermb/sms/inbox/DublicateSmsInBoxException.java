package com.rssl.phizic.business.ermb.sms.inbox;

/**
 * @author Gulov
 * @ created 11.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Exception, выбрасывается при сохранении в таблицу SMS_INBOX, в случае дублирования записей
 */
public class DublicateSmsInBoxException extends Exception
{
	public DublicateSmsInBoxException()
	{
		super("СМС с таким номером телефона и текстом уже существует.");
	}

	public DublicateSmsInBoxException(String message)
	{
		super(message);
	}

	public DublicateSmsInBoxException(Throwable cause)
	{
		super(cause);
	}
}
