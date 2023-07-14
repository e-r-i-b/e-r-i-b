package com.rssl.phizic.locale.replicator;

/**
 * Режим синхронизации текстовок
 * @author koptyaev
 * @ created 16.10.2014
 * @ $Author$
 * @ $Revision$
 */
public enum SyncMode
{
	//Обновить записи в базе данных, не заводя новых и не удаляя
	UPDATE_ONLY,
	//Полностью синхронизировать записи, удаление, добавление, обновление
	FULL_SYNC
}
