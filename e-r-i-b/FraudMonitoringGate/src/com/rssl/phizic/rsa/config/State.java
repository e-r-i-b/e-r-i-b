package com.rssl.phizic.rsa.config;

/**
 * Состояние внешней системы RSA
 *
 * @author khudyakov
 * @ created 04.05.15
 * @ $Author$
 * @ $Revision$
 */
public enum State
{
	ACTIVE_INTERACTION,                 //активна, полное взаимодейстивие (отправка запроса, обработка ответа, изменение поведения ЕРИБ в соответствии с ответом)
	ACTIVE_ONLY_SEND,                   //активна, только отправка сообщений из ЕРИБ
	NOT_ACTIVE,                         //не активна
}
