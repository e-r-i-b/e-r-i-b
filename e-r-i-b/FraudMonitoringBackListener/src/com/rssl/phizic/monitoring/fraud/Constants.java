package com.rssl.phizic.monitoring.fraud;

/**
 * @author khudyakov
 * @ created 22.06.15
 * @ $Author$
 * @ $Revision$
 */
public interface Constants
{
	static String CLIENT_NOT_FOUND_ERROR_MESSAGE                            = "Профиль клиента personId = %s, clientTransactionId = %s не найден в БД блока.";
	static String BLOCK_CLIENT_PROFILE_BLOCKING_INFO_MESSAGE                = "Профиль клиента personId = %s, clientTransactionId = %s в блоке заблокирован.";
	static String CSA_CLIENT_PROFILE_BLOCKING_INFO_MESSAGE                  = "Профиль клиента personId = %s, clientTransactionId = %s в ЦСА заблокирован.";
	static String NOT_FOUND_REQUEST_ERROR_MESSAGE                           = "Получен пустой запрос на блокировку профиля клиента.";
	static String CLIENT_BLOCK_REASON                                       = "Системная блокировка по подозрению в мошейничестве, по сообщению от ВС ФМ.";
	static String CLIENT_BLOCK_SMS_KEY                                      = "BLOCK.FM.client.profile";
	static String LOCKER_FIO                                                = "SYSTEM";
}
