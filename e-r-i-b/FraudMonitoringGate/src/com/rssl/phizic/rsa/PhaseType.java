package com.rssl.phizic.rsa;

/**
 * Стадия проверки во ФМ
 *
 * @author khudyakov
 * @ created 21.05.15
 * @ $Author$
 * @ $Revision$
 */
public enum PhaseType
{
	SENDING_REQUEST,            //фаза отправки запроса во ФМ (для асинхронного взаимодействия)
	WAITING_FOR_RESPONSE,       //фаза ожидания ответа (для асинхронного взаимодействия)
	CONTINUOUS_INTERACTION,     //непрерывная обработка (для синхронного взаимодействия)
	OFF_LINE                    //off-line
}
