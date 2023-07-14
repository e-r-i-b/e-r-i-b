package com.rssl.phizic.gate.mobilebank;

/**
 * Режим получения регистраций
 * @author niculichev
 * @ created 15.02.2013
 * @ $Author$
 * @ $Revision$
 */
public enum GetRegistrationMode
{
	/**
	 *  поиск регистраций только по платежным картам
	 */
	SOLID,

	/**
	 * Поиск по всем картам(поиск всех регистраций)
	 */
	SOLF,

	/**
	 * Сначала поиск по платежным картам, если не найдено, поиск по всем
	 */
	BOTH
}
