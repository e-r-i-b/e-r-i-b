package com.rssl.phizic.gate.einvoicing;

import java.util.Calendar;

/**
 * Уведомление о выполненной проводке или отмене.
 *
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public interface ShopNotification
{
	/**
	* @return Внешний идентификатор заказа в ЕРИБ
	*/
	public String getUuid();

	/**
	* @return Внутренний идентификатор заказа/документа отмены/возврата у получателя
	*/
	public String getExternalId();

	/**
	* @return Уникальный код операции SVFE
	*/
	public String getUtrrno();

	/**
	* @return Статус документа
	*/
	public String getState();

	/**
	* @return Статус оповещения
	*/
	public NotificationStatus getNotifStatus();

	/**
	 * Установить статус оповещения
	* @param status Статус оповещения
	*/
	public void setNotifStatus(NotificationStatus status);

	/**
	* @return Время оповещения
	*/
	public Calendar getNotifTime();

	/**
	 * Установить время оповещения
	 * @param notifTime - время оповещения
	 */
	public void setNotifTime(Calendar notifTime);

	/**
	* @return Количество попыток оповещения
	*/
	public Long getNotifCount();

	/**
	 * Установить количество попыток оповещения
	* @param count Количество попыток оповещения
	*/
	public void setNotifCount(Long count);

	/**
	* @return Текст ошибки (для оповещений, завершившихся ошибкой)
	*/
	public String getNotifStatusDescription();

	/**
	 * Установить текст ошибки
	 * @param notifStatusDescription - текст ошибки
	 */
	public void setNotifStatusDescription(String notifStatusDescription);

	/**
	* @return Код получателя
	*/
	public String getReceiverCode();

	/**
	 * @return дата создания оповещения
	 */
	public Calendar getDate();
}
