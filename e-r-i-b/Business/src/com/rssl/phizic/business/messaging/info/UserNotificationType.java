package com.rssl.phizic.business.messaging.info;

/**
 * Тип оповещения
 * @ author gorshkov
 * @ created 03.09.13
 * @ $Author$
 * @ $Revision$
 */
public enum UserNotificationType
{
	loginNotification,     //оповещение клиента о входе в систему
	mailNotification,      //оповещение о получении письма из службы помощи
	operationNotification, //оповещение об исполнении операций
	newsNotification;      //рассылка новостей банка
}
