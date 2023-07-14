package com.rssl.phizic.business.smsbanking.pseudonyms;

/**
 * @author hudyakov
 * @ created 13.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class NullPseudonymException extends Exception
{
	/**
	 * конструктор по сообщению
	 * @param message
	 */
	public NullPseudonymException(String message)
    {
        super(message);
    }

    public NullPseudonymException(Throwable cause)
    {
        super(cause);
    }

	public NullPseudonymException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
