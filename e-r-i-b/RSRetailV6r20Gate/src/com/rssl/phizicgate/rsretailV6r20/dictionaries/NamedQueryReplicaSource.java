package com.rssl.phizicgate.rsretailV6r20.dictionaries;

import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizicgate.rsretailV6r20.data.RSRetailV6r20Executor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;
import javax.naming.NameNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 25.10.2005
 * Time: 16:16:11
 */
public abstract class NamedQueryReplicaSource implements ReplicaSource
{
    protected Session session;
    protected String namedQuery;

    protected NamedQueryReplicaSource(String namedQuery)
    {
        this.namedQuery = namedQuery;
    }

    public Iterator iterator()
    {
        if(session != null)
            throw new RuntimeException("Обнаружена незакрытая сессия, вызовите метод close()!");

	    try
	    {
			//noinspection HibernateResourceOpenedButNotSafelyClosed
		    session = RSRetailV6r20Executor.getSessionFactory().openSession();
	    }
	    catch (NameNotFoundException e)
	    {
		    throw new RuntimeException(e);
	    }

	    Query query = session.getNamedQuery(namedQuery);
        List list = query.list();

        return list.iterator();
    }

    public void close()
    {
        if(session == null)
            return;

        session.close();
        session = null;
    }
}
