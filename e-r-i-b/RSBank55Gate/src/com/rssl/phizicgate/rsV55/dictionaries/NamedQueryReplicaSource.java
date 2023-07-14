package com.rssl.phizicgate.rsV55.dictionaries;

import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.dictionaries.SynchKeyComparator;
import com.rssl.phizicgate.rsV55.data.GateRSV55Executor;
import org.hibernate.Session;
import org.hibernate.Query;

import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import javax.naming.NameNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 25.10.2005
 * Time: 16:16:11
 */
public abstract class NamedQueryReplicaSource implements ReplicaSource
{
    private Session session;
    private String namedQuery;
	private boolean needSort;

    protected NamedQueryReplicaSource(String namedQuery, boolean needSort)
    {
        this.namedQuery = namedQuery;
	    this.needSort = needSort;
    }

    public Iterator iterator()
    {
        if(session != null)
            throw new RuntimeException("Обнаружена незакрытая сессия, вызовите метод close()!");

	    try
	    {
		    //noinspection HibernateResourceOpenedButNotSafelyClosed
		    session = GateRSV55Executor.getSessionFactory().openSession();
	    }
	    catch (NameNotFoundException e)
	    {
		    throw new RuntimeException(e);
	    }

	    Query query = session.getNamedQuery(namedQuery);
        List list = query.list();
	    if(needSort)
	        Collections.sort(list, new SynchKeyComparator());
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
