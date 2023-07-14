package com.rssl.phizic.session;

import com.rssl.phizic.person.Person;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 17.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация сессии клиента
 */
class PersonSessionImpl implements PersonSession
{
	private final Person person;

	private final String id;

	private final Map<String, Object> store = Collections.synchronizedMap(new HashMap<String, Object>());

	///////////////////////////////////////////////////////////////////////////

	PersonSessionImpl(Person person)
	{
		this.person = person;
		this.id = "PersonSession" + person.getId();
	}

	public Person getPerson()
	{
		return person;
	}

	public String getId()
	{
		return id;
	}

	public void save(String key, Object obj)
	{
		store.put(key,obj);
	}

	public Object restore(String key)
	{
		return store.get(key);
	}

	public void clear()
	{
		store.clear();
	}

	public void remove(String key)
	{
		store.remove(key);
	}

	public Object getSyncObject()
	{
		return Collections.unmodifiableMap(store);
	}
}
