package com.rssl.phizic.gate.claims.sbnkd;

/**
 * @author bogdanov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 */

public enum CardInfoStatus
{
	/**
	 * Только что созданная заявка по карте.
	 */
	NEW,
	/**
	 * отправлен запрос на создание карты.
	 */
	CONTRACTED,
	/**
	 * получен ответ на запрос на создание карты.
	 */
	CREATE_CRD,
	/**
	 * Ошибка при создании карты.
	 */
	ERROR,
	/**
	 * Исполнено.
	 */
	COMPLETE
}
