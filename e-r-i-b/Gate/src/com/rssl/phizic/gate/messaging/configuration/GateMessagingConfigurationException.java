package com.rssl.phizic.gate.messaging.configuration;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Roshka
 * @ created 15.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class GateMessagingConfigurationException extends GateException
{

	public GateMessagingConfigurationException(String message)
	{
		super(message);
	}

	public GateMessagingConfigurationException(Throwable cause)
	{
		super(cause);
	}

	public GateMessagingConfigurationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}