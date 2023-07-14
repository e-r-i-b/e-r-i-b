package com.rssl.phizic.notifications;

/**
 * @author hudyakov
 * @ created 14.10.2009
 * @ $Author$
 * @ $Revision$
 */

public class NotificationException extends Exception
{
	public NotificationException(String message)
    {
        super(message);
    }

    public NotificationException(Throwable cause)
    {
        super(cause);
    }

	public NotificationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
