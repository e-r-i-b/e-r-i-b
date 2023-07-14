package com.rssl.phizic.messaging;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author Erkin
 * @ created 03.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class IKFLMessagingLogicException extends LogicException
{
	public IKFLMessagingLogicException(String message)
	{
		super(message);
	}

	public IKFLMessagingLogicException(Throwable cause)
	{
		super(cause);
	}

	public IKFLMessagingLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
