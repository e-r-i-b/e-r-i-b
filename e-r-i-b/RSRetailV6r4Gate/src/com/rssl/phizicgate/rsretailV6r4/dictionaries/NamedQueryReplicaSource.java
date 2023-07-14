package com.rssl.phizicgate.rsretailV6r4.dictionaries;

import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;
import java.sql.Statement;
import java.sql.SQLException;
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

    public Iterator iterator() throws GateException
    {
	    try
	    {
			return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<Iterator>()
			{
				public Iterator run(Session session)
				{
					Query query = session.getNamedQuery(namedQuery);
					return query.list().iterator();
				}
			});
	    }
	    catch(Exception e)
	    {
		    throw new GateException(e);
	    }
    }

    public void close()
    {
        if(session == null)
            return;

        session.close();
        session = null;
    }
}
