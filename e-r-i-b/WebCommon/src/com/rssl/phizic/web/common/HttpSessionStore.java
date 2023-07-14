package com.rssl.phizic.web.common;

import com.rssl.phizic.utils.store.Store;

import javax.servlet.http.HttpSession;

/**
 * @author mihaylov
 * @ created 25.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Хранилище на базе http сессии. Сессия передается в конструкторе, а не тащится из реквеста.
 * Необходимо для тех мест, где нет реквеста.
 */
public class HttpSessionStore implements Store
{
	private HttpSession session;

	/**
	 * Конструктор хранилища
	 * @param session - сессия
	 */
	public HttpSessionStore(HttpSession session)
	{
		this.session = session;
	}

	public String getId()
	{
		return session.getId();
	}

	public void save(String key, Object obj)
	{
		session.setAttribute(key,obj);
	}

	public Object restore(String key)
	{
		return session.getAttribute(key);
	}

	public void clear()
	{
	}

	public void remove(String key)
	{
		session.removeAttribute(key);
	}

	public Object getSyncObject()
	{
		return session;
	}
}
