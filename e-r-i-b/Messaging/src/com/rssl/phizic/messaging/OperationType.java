package com.rssl.phizic.messaging;

/**
 * Тип операции
 *
 * @author khudyakov
 * @ created 14.11.2012
 * @ $Author$
 * @ $Revision$
 */
public enum OperationType
{
	LOGIN,                          //подтверждение входа клиента
	UNUSUAL_IP,                     //вход с нестандартного IP
	PAYMENT_OPERATION,              //подтверждение платежной операции
	CREATE_TEMPLATE_OPERATION,      //подтверждение шаблона операции
	EDIT_USER_SETTINGS,             //подтверждение изменения параметров в личном кабинете
	REGISTRATION_OPERATION,         //подтверждение операции регистрации пользователя
	CALLBACK_BCI_CONFIRM_OPERATION, //подтверждение разрешения запроса в БКИ при заказе обратного звонка для расширенных кредитных заявок
	CALLBACK_CONFIRM_OPERATION,     //подтверждение запроса заказа обратного звонка для расширенных кредитных заявок
	VIEW_OFFER_TEXT_OPERATION       //подтверждение запроса заказа обратного звонка для расширенных кредитных заявок
}
