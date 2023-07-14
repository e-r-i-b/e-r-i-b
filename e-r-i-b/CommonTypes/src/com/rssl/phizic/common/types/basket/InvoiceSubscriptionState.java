package com.rssl.phizic.common.types.basket;

/**
 * @author osminin
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Статус автопоиска по счетам
 */
public enum InvoiceSubscriptionState
{
	ACTIVE,         //активный
	STOPPED,        //приостановленный
	INACTIVE,       //неактивный
	ERROR,          //ошибка
	WAIT,           //ожидает обработки
	DELETED,        //удаленный
	AUTO,           // автоматически сгенерированный скриптом
	DELAY_DELETE,    // ожидающий оффлайн удаление
	DELAY_CREATE,    // ожидающий оффлайн создание
	DRAFT,          // шаблон для создания
	FAKE_SUBSCRIPTION_PAYMENT  //статус, указывающий что данная сущность - заявка на создание, конвертированная в псевдо-подписку (необходимо для визуального отображения на странице "Корзины платежей")
}
