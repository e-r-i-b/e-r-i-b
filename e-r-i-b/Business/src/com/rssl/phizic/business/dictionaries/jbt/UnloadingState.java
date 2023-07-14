package com.rssl.phizic.business.dictionaries.jbt;

/**
 * @author akrenev
 * @ created 23.01.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Режимы выгрузки ЖБТ
 * */
public enum UnloadingState
{
	/**
	 * все поставщики услуг
	 * */
	ALL,
	/**
	 * все, кроме выбранных
	 * */
	EXCEPT,
	/**
	 * выбранные поставщики услуг
	 * */
	SELECT
}
