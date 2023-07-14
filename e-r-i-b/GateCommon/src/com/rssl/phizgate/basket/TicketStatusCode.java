package com.rssl.phizgate.basket;

/**
 * @author vagin
 * @ created 29.04.14
 * @ $Author$
 * @ $Revision$
 * Статус код квитанции о получении инвойса.
 */
public enum TicketStatusCode
{
	OK(0),
	TIMEOUT(-105),
	ERROR(-100),
	PAYMENT_NOT_FOUND(-122);

	private long code;

	public long getCode()
	{
		return code;
	}

	TicketStatusCode(long code)
	{
		this.code = code;
	}
}
