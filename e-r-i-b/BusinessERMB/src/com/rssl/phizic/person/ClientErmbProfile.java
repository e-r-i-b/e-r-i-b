package com.rssl.phizic.person;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;
import com.rssl.phizic.utils.PhoneNumber;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 31.07.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ЕРМБ-профиль клиента
 * Содержит настройки услуги СМС-банкинга по клиенту
 */
@PlainOldJavaObject
@SuppressWarnings("PublicField")
public class ClientErmbProfile
{
	/**
	 * Идентификатор профиля
	 */
	public long id;

	/**
	 * Статус подключения услуги
	 * true - услуга подключена
	 * false - услуга не подключена
	 */
	public boolean serviceStatus;

	/**
	 * Статус клиентской блокировки
	 * true - услуга заблокирована клиентом
	 * false - услуга не заблокирована клиентом
	 */
	public boolean clientBlocked;

	/**
	 * Статус блокировки по неоплате
	 * true - услуга заблокирована по неоплате
	 * false - услуга не заблокирована по неоплате
	 */
	public boolean paymentBlocked;

	/**
	 * Главный телефон, подключённый к услуге.
	 * Обязательно указан, если услуга подключена.
	 * Обязательно входит в перечень телефонов, если указан.
	 * Может быть не указан, если услуга не подключена.
	 */
	public PhoneNumber mainPhone;

	/**
	 * Список телефонов, подключённых к услуге, включая главный телефон.
	 * Обязательно не пуст, если услуга подключена.
	 * Может быть пуст, если услуга не подключена.
	 */
	public Collection<PhoneNumber> phones;

	/**
	 * Версия профиля
	 */
	public long version;
}
