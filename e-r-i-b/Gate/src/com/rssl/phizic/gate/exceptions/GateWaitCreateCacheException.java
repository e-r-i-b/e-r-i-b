package com.rssl.phizic.gate.exceptions;

/**
 * @author osminin
 * @ created 13.08.15
 * @ $Author$
 * @ $Revision$
 *
 * Исключение, сигнализирующее о том, что метод еще не завершил свое выполенение и кэш для метода еще не построен.
 */
public class GateWaitCreateCacheException extends GateLogicException
{
	/**
	 * ctor
	 * @param message сообщение об ощибке
	 */
	public GateWaitCreateCacheException(String message)
	{
		super(message);
	}
}
