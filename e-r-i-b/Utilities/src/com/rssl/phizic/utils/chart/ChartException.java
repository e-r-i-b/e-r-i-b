package com.rssl.phizic.utils.chart;

import com.rssl.phizic.common.types.exceptions.IKFLException;

/**
 * @author akrenev
 * @ created 01.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Исключение генерации диаграммы
 */

public class ChartException extends IKFLException
{
	/**
	 * констрактор
	 * @param cause исключение-источник
	 */
	public ChartException(Throwable cause)
	{
		super(cause);
	}
}
