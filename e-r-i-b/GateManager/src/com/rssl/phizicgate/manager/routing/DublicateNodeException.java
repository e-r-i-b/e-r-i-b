package com.rssl.phizicgate.manager.routing;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author akrenev
 * @ created 09.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class DublicateNodeException extends GateLogicException
{
	public DublicateNodeException()
	{
		super("Узел с таким адресом уже существует.");
	}
}
