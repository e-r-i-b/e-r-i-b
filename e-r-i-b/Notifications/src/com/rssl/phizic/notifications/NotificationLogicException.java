package com.rssl.phizic.notifications;

/**
 * @author hudyakov
 * @ created 14.10.2009
 * @ $Author$
 * @ $Revision$
 */

public class NotificationLogicException extends Exception
{
	public NotificationLogicException(String message)
    {
        super(message);
    }

    public NotificationLogicException(Throwable cause)
    {
        super(cause);
    }

	public NotificationLogicException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
