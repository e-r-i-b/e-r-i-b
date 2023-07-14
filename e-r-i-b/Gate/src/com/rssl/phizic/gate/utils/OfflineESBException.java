package com.rssl.phizic.gate.utils;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * Исключение, сигнализирующее о недоступности шины в целом
 * @author Pankin
 * @ created 04.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class OfflineESBException extends GateLogicException
{
	public OfflineESBException()
	{
	}

	public OfflineESBException(String message)
	{
		super(message);
	}

	public OfflineESBException(Throwable cause)
	{
		super(cause);
	}

	public OfflineESBException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
