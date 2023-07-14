package com.rssl.phizic.notifications;

/**
 * @author hudyakov
 * @ created 13.10.2009
 * @ $Author$
 * @ $Revision$
 */

public class BusinessNotificationException extends NotificationException
{
	public BusinessNotificationException(String message)
    {
        super(message);
    }

    public BusinessNotificationException(Throwable cause)
    {
        super(cause);
    }

	public BusinessNotificationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
