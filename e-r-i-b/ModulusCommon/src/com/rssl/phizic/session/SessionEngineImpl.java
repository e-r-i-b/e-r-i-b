package com.rssl.phizic.session;

import com.rssl.phizic.engine.EngineBase;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.module.loader.LoadOrder;
import com.rssl.phizic.module.work.WorkManager;
import com.rssl.phizic.person.Person;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Erkin
 * @ created 16.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация сессионного движка
 */
public class SessionEngineImpl extends EngineBase implements SessionEngine
{
	/**
	 * Мапа "УИК -> сессия"
	 * (УИК = Униеверсальный Идентификатор Клиента)
	 */
	private final ConcurrentHashMap<Long, SessionEntry> sessions = new ConcurrentHashMap<Long, SessionEntry>();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param manager - менеджер по движкам
	 */
	public SessionEngineImpl(EngineManager manager)
	{
		super(manager);
	}

	public LoadOrder getLoadOrder()
	{
		return LoadOrder.SESSION_ENGINE_ORDER;
	}

	public void start()
	{
	}

	public void stop()
	{
		sessions.clear();
	}

	public PersonSession getSession(Person person, boolean createIfNone)
	{
		if (person == null)
			throw new IllegalArgumentException("Аргумент 'person' не может быть null");

		PersonSession session;
		if (createIfNone)
			session = createSessionIfNone(person);
		else
			session = getExistsSession(person);

		if (session != null)
		{
			// Не забываем положить в контекст потока найденную сессию
			// Без этого не заработает куча кода, ориентированного на контекст потока
			WorkManager workManager = getModule().getWorkManager();
			workManager.setSession(session);
		}

		return session;
	}

	private PersonSession getExistsSession(Person person)
	{
		SessionEntry sessionEntry = sessions.get(person.getId());
		if (sessionEntry != null)
			return sessionEntry.session;
		else
			return null;
	}

	private PersonSession createSessionIfNone(Person person)
	{
		SessionEntry newSessionEntry = new SessionEntry();
		SessionEntry sessionEntry = sessions.putIfAbsent(person.getId(), newSessionEntry);
		if (sessionEntry == null)
			sessionEntry = newSessionEntry;

		if (sessionEntry.session == null)
		{
			//noinspection SynchronizationOnLocalVariableOrMethodParameter
			synchronized (sessionEntry)
			{
			    if (sessionEntry.session == null)
			    {
				    PersonSession session = new PersonSessionImpl(person);
				    sessionEntry.session = session;
				    if (log.isDebugEnabled())
					    log.debug("Создана сессия " + session.getId());
			    }
			}
		}

		return sessionEntry.session;
	}

	public void destroySession(PersonSession session)
	{
		if (session == null)
			return; // ну не дали сессию и ладно

		if (sessions.remove(session.getPerson().getId()) != null) {
			if (log.isDebugEnabled())
				log.debug("Удалена сессия " + session.getId());
		}
		session.clear();
	}

	///////////////////////////////////////////////////////////////////////////

	private static class SessionEntry
	{
		private PersonSession session;
	}
}
