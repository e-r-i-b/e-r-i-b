package com.rssl.phizic.logging.system;

/**
 *
 * Для отладки каких-либо операций
 *
 * @ author: Gololobov
 * @ created: 08.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class DebugLogConstructorImpl implements LogConstructor
{
	public DebugLogImpl newInstance(LogModule name)
	{
		return new DebugLogImpl(name);
	}
}
