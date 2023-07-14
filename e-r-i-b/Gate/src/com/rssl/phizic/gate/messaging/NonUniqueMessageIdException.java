package com.rssl.phizic.gate.messaging;

import com.rssl.phizic.gate.messaging.GateMessagingException;

/**
 * Используется в случает 
 * @author Evgrafov
 * @ created 29.08.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3719 $
 */
public class NonUniqueMessageIdException extends GateMessagingException
{
	public NonUniqueMessageIdException(String message)
	{
		super(message);
	}
}