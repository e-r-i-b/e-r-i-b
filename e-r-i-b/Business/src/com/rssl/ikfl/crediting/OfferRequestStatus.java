package com.rssl.ikfl.crediting;

/**
 * @author Erkin
 * @ created 26.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Статус выполнения запроса получения предодобренных предложений
 */
public enum OfferRequestStatus
{
	/**
	 * Запрос выполняется, ждём ответа
	 */
	IN_PROGRESS,

	/**
	 * Запрос выполнен успешно
	 */
	SUCCEEDED,

	/**
	 * Запрос выполнен неуспешно
	 */
	FAILED,
}
