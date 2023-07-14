package com.rssl.phizic.business;

/**
 * @author Dorzhinov
 * @ created 15.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class NotActiveMobileBankTemplateException extends BusinessException
{
    public NotActiveMobileBankTemplateException(String message)
    {
        super(message);
    }

    public NotActiveMobileBankTemplateException(Throwable cause)
    {
        super(cause);
    }

    public NotActiveMobileBankTemplateException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
