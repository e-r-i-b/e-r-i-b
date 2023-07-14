package com.rssl.phizic.gate.longoffer;

/**
 * Период, в течении которого производится подсчет общей суммы списания
 * @author niculichev
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */
public enum TotalAmountPeriod
{
	/**
	 * В течении дня
	 */
	IN_DAY,

	/**
	 * В течении недели
	 */
	IN_WEEK,

	/**
	 * В течении декады
	 */
	IN_TENDAY,

	/**
	 * В течении месяца
	 */
	IN_MONTH,

	/**
	 * В течении квартала
	 */
	IN_QUARTER,

	/**
	 * В течении года
	 */
	IN_YEAR
}
