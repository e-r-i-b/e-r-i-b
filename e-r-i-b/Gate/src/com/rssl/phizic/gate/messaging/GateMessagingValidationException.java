package com.rssl.phizic.gate.messaging;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.errors.ErrorMessage;

/**
 * »спользуетс€ в тех случа€х, когда отказ свидетельствует
 * о некорректной работе одной из систем
 * @author Evgrafov
 * @ created 29.08.2006
 * @ $Author: krenev $
 * @ $Revision: 16354 $
 */
public class GateMessagingValidationException extends GateException
{
	private ErrorMessage errorMessage;

	public ErrorMessage getErrorMessage()
	{
		return errorMessage;
	}

	public GateMessagingValidationException(String message)
    {
        super(message);
    }

	public GateMessagingValidationException(ErrorMessage errorMessage)
    {
        super(errorMessage.getMessage());
	    this.errorMessage = errorMessage;
    }
}