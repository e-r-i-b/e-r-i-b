package com.rssl.auth.csa.wsclient.exceptions;

/**
 * Исключение, сигнализирующее о временной блокировке коннектора
 * @author Pankin
 * @ created 03.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class ConnectorBlockedException extends BackLogicException
{
	private final Long badAuthDelay;

	public ConnectorBlockedException(String message, Long badAuthDelay)
	{
		super(message);
		this.badAuthDelay = badAuthDelay;
	}

	public Long getBadAuthDelay()
	{
		return badAuthDelay;
	}
}
