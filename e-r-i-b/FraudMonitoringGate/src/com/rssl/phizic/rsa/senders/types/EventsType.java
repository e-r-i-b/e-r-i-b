package com.rssl.phizic.rsa.senders.types;

/**
 * События, относительно которых необходимо отправить запрос во Фрод-мониторинг
 *
 * @author khudyakov
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */
public enum EventsType
{
	VIEW_STATEMENT("VIEW_STATEMENT"),                         //клиент инициировал запрос о просмотре истории операций
	UPDATE_USER("UPDATE_USER"),                               //клиент изменил данные
	ENROLL("ENROLL"),                                         //клиент завершил регистрацию
	PAYMENT("PAYMENT"),                                       //оплата
	ADD_PAYEE("ADD_PAYEE"),                                   //клиент добавляет новый шаблон
	EDIT_PAYEE("EDIT_PAYEE"),                                 //редактирования шаблона клиентом
	CHANGE_LOGIN_ID("CHANGE_LOGIN_ID"),                       //смена логина
	CHANGE_PASSWORD("CHANGE_PASSWORD"),                       //смена пароля
	CHANGE_ALERT_SETTINGS("CHANGE_ALERT_SETTINGS"),           //изменение настроек информирования
	SESSION_SIGNIN("SESSION_SIGNIN"),                         //вход пользователя в систему
	FAILED_LOGIN_ATTEMPT("FAILED_LOGIN_ATTEMPT"),             //неуспешный вход в систему
	FAILED_MOBILE_LOGIN_ATTEMPT("FAILED_LOGIN_ATTEMPT"),      //неуспешный вход в систему через мобильное приложение
	REQUEST_CREDIT("REQUEST_CREDIT"),                         //заявка на кредит
	CLIENT_DEFINED("CLIENT_DEFINED"),                         //заявка на УДБО
	REQUEST_NEW_CARD("REQUEST_NEW_CARD");                     //заявка на перевыпуск карты

	private String description;

	EventsType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
