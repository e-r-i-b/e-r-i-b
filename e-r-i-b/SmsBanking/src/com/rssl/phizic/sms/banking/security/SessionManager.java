package com.rssl.phizic.sms.banking.security;

import com.rssl.phizic.business.web.session.SimpleSession;

import java.util.Map;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * @author hudyakov
 * @ created 17.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class SessionManager
{
	private static Map<String, SimpleSession> sessions = new Hashtable<String, SimpleSession>();

    public static SimpleSession getSession(String idSession)
    {
        SimpleSession session = sessions.get(idSession);
        if (session != null)
	        return session;
        session = new SimpleSession(idSession);
        sessions.put(idSession, session);
        return session;
    }

	public static void removeExpiredSession()
	{
		for ( Iterator iterator = sessions.values().iterator(); iterator.hasNext(); )
		{
			SimpleSession session = (SimpleSession) iterator.next();
			if (session.isExpired())
			{
				iterator.remove();
			}
		}
	}
}
