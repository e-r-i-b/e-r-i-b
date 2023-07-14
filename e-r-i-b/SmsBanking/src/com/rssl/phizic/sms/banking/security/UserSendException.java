package com.rssl.phizic.sms.banking.security;

/**
 * @author emakarov
 * @ created 10.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class UserSendException extends Exception
{
	/**
	 * конструктор по сообщению
	 * @param message
	 */
	public UserSendException(String message)
    {
        super(message);
    }

	/**
	 * конструктор номер 2
	 * @param cause
	 */
    public UserSendException(Throwable cause)
    {
        super(cause);
    }

	/**
	 * конструктор по сообщению + конструктор номер 2, а вообще - все они super
	 *
	 * Constructs a new throwable with the specified detail message and cause.
	 * 
	 * @param message
	 * @param cause
	 */
	public UserSendException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
