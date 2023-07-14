package com.rssl.phizic.business.web;

import com.rssl.phizic.business.web.session.SimpleSession;
import com.rssl.phizic.utils.store.Store;

/**
 * @author hudyakov
 * @ created 14.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class SimpleSessionStore implements Store
{
	private SimpleSession session;

    public SimpleSessionStore(SimpleSession session)
    {
        this.session = session;
    }

	public String getId()
	{
		return session.getId();
	}

	public void save(String key, Object obj)
    {
        session.setAttribute(key, obj);
	}

    public Object restore(String key)
    {
        return session.getAttribute(key);
    }

	public void clear()
	{
		session.invalidate();
	}

	public void remove(String key)
	{
		session.remove(key);
	}

	public Object getSyncObject()
	{
		return session;
	}
}
