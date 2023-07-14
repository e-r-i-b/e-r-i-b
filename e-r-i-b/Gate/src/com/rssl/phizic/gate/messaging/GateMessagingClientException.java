package com.rssl.phizic.gate.messaging;

import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.errors.ErrorMessage;

/**
 * Используется в тех случаях когда причина может быть сообщена клиенту АС "ЭСК"
 * @author Evgrafov
 * @ created 29.08.2006
 * @ $Author: rtishcheva $
 * @ $Revision: 72237 $
 */
public class GateMessagingClientException extends GateLogicException
{
	private ErrorMessage errorMessage;

	public ErrorMessage getErrorMessage()
	{
		return errorMessage;
	}

	public GateMessagingClientException(GateMessagingClientException e)
    {
        super(e);
    }

	public GateMessagingClientException(String message, String errCode)
    {
        super(message, errCode);
    }

	public GateMessagingClientException(String message)
    {
        super(message);
    }

	public GateMessagingClientException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public GateMessagingClientException(ErrorMessage errorMessage)
    {
        super(errorMessage.getMessage());
	    this.errorMessage = errorMessage;	    
    }
}