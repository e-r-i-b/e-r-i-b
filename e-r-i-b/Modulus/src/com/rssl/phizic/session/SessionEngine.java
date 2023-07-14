package com.rssl.phizic.session;

import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Движок сессий пользователя
 * Умеет создавать/выдавать сессии пользователя
 */
public interface SessionEngine
{
	/**
	 * Возвращает/создаёт сессию клиента
	 * @param person - клиент
	 * @param createIfNone - флажок "создавать сессию, если не найдена"
	 * @return сессия или null, если <createIfNone>=false и готовой сессии не нашлось
	 * ВАЖНО! Созданная/найденная сессия будет положена в переменные потока (тред-локал)
	 * (посредством WorkManager.setSession)
	 */
	PersonSession getSession(Person person, boolean createIfNone);

	/**
	 * Удаляет сессию
	 * @param session - сессия, которую нужно удалить (can be null)
	 */
	void destroySession(PersonSession session);
}
