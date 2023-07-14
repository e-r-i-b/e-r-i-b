package com.rssl.phizic.gate.cache;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.depo.DepoAccount;

/**
 * @author mihaylov
 * @ created 19.11.2010
 * @ $Author$
 * @ $Revision$
 */
/*
Сервис для принудительного обновления кеша. Пока данные действия необходимы только для депозитария
и являютя очень спицифичным функционалом, поэтому вынес в отдельный сервис
 */
public interface RefreshCacheService extends Service
{
	/**
	 * Удаляет объект из кеша и чистит кеш по связонным с ним сущностям,
	 * если объект пролежал в кеше больше чем seconds
	 * @param depoAccount - счет депо
	 * @param seconds - время, после которого объект необходимо удалять из кеша
	 * @return true - объект был удален
	 */
	boolean refreshDepoCacheService(DepoAccount depoAccount, int seconds);

}
