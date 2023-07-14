package com.rssl.phizic.gate.ips;

/**
 * @author Erkin
 * @ created 29.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Категория карточной операции
 */
public interface CardOperationCategory
{
	/**
	 * @return признак "доходная / расходная операция"
	 */
	boolean isIncome();
}
