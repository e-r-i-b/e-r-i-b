package com.rssl.phizic.common.types.ermb;

/**
 * Статус услуги ЕРМБ
 * @author Puzikov
 * @ created 09.09.14
 * @ $Author$
 * @ $Revision$
 */

public enum ErmbStatus
{
	ACTIVE,             //активная услуга
	NOT_CONNECTED,      //нет подключения ЕРМБ
	UNPAID,             //заблокирована по неоплате
	BLOCKED             //клиентская блокировка
}
