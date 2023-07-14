package com.rssl.phizic.web.actions;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 03.10.2005 Time: 19:55:57 */
public class NoActiveOperationException extends Exception
{
    public NoActiveOperationException(String message)
    {
        super(message);
    }

    public NoActiveOperationException(Throwable cause)
    {
        super(cause);
    }

    public NoActiveOperationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
