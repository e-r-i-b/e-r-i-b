package com.rssl.phizicgate.manager.routing;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author akrenev
 * @ created 09.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class DublicateAdapterException extends GateLogicException
{
	public DublicateAdapterException()
	{
		super("Адаптер с таким идентификатором уже существует.");
	}
}
