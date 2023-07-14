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
 * ���������� ����������� ������
 */
public class SessionEngineImpl extends EngineBase implements SessionEngine
{
	/**
	 * ���� "��� -> ������"
	 * (��� = �������������� ������������� �������)
	 */
	private final ConcurrentHashMap<Long, SessionEntry> sessions = new ConcurrentHashMap<Long, SessionEntry>();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param manager - �������� �� �������
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
			throw new IllegalArgumentException("�������� 'person' �� ����� ���� null");

		PersonSession session;
		if (createIfNone)
			session = createSessionIfNone(person);
		else
			session = getExistsSession(person);

		if (session != null)
		{
			// �� �������� �������� � �������� ������ ��������� ������
			// ��� ����� �� ���������� ���� ����, ���������������� �� �������� ������
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
					    log.debug("������� ������ " + session.getId());
			    }
			}
		}

		return sessionEntry.session;
	}

	public void destroySession(PersonSession session)
	{
		if (session == null)
			return; // �� �� ���� ������ � �����

		if (sessions.remove(session.getPerson().getId()) != null) {
			if (log.isDebugEnabled())
				log.debug("������� ������ " + session.getId());
		}
		session.clear();
	}

	///////////////////////////////////////////////////////////////////////////

	private static class SessionEntry
	{
		private PersonSession session;
	}
}
