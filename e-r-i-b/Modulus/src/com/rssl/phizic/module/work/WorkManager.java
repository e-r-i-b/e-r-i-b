package com.rssl.phizic.module.work;

import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.utils.store.Store;

/**
 * @author Erkin
 * @ created 15.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Менеджер рабочего процесса (потока)
 * Основная задача - настройка переменных потока для выполнения поставленной задачи
 * ВАЖНО! Менеджер работает только с "текущим" потоком
 */
@ThreadSafe
public interface WorkManager
{
	/**
	 * Начать работу с потоком
	 */
	void beginWork();

	/**
	 * Установить сессию в поток
	 * @param session - сессия (can be null)
	 */
	void setSession(Store session);

	/**
	 * Вернуть сессию потока
	 * @return сессия или null, если нет/не используется модулем
	 */
	Store getSession();

	/**
	 * Закончить работу с потоком
	 */
	void endWork();
}
