package com.rssl.phizic.logging;

/**
 * Акцессор для вычисления доступа к логированию
 * @author gladishev
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */
public interface LoggingAccessor
{
	/**
	 * @param parameters - параметры
	 * @return Разрешено ли логирование в обычном режиме
	 */
	boolean needNormalModeLogging(Object... parameters);

	/**
	 * @param parameters - параметры
	 * @return Разрешено ли логирование в расширенном режиме
	 */
	boolean needExtendedModeLogging(Object... parameters);
}
