package com.rssl.phizic.business.shop;

/**
 * User: Moshenko
 * Date: 28.11.2011
 * Time: 16:18:58
 * Статусы оповещений  магазина по заказам
 */
public enum OrderStatus
{
	ERROR,     //На уведомление вернулась ошибка
	OK,        //уведомление прошло успешно (больше не высылаем)
	NOT_SEND , //уведомление не посылалось
	SUSPENDED  //уведомление не прошло ни разу (больше не высылаем)
}
