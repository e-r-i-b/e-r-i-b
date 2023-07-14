package com.rssl.phizic.gate.messaging;

/**
 * @author Roshka
 * @ created 12.09.2006
 * @ $Author$
 * @ $Revision$
 */

public class NotExpectedResponseException extends RuntimeException
{
	public NotExpectedResponseException(String message)
	{
		super(message);
	}
}