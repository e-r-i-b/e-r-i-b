package com.rssl.phizic.gate.mobilebank;

/**
 *
 * Значения которые может принимать статус Быстрых сервисов
 *
 * User: Balovtsev
 * Date: 24.05.2012
 * Time: 9:40:48
 */
public enum QuickServiceStatusCode
{
	/*
	 * Подключенно
	 */
	QUICK_SERVICE_STATUS_ON,

	/*
	 * Отключнно
	 */
	QUICK_SERVICE_STATUS_OFF,

	/*
	 * Статус показывающий, что возникла ошибка исправимая повторным запросом
	 */
	QUICK_SERVICE_STATUS_REPEAT_QUERY,

	/*
	 * Статус показывающий, что возникла критичная, неисправимая ошибка(неверный параметр при вызове процедуры
	 * и т.д)
	 */
	QUICK_SERVICE_STATUS_UNKNOWN;
}
